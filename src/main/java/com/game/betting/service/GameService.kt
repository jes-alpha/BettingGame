package com.game.betting.service

import com.game.betting.exception.NotEnoughFundsException
import com.game.betting.model.Bet
import com.game.betting.model.Player
import com.game.betting.model.TransactionType
import com.game.betting.repository.PlayerRepository
import com.game.betting.util.RandomNumberGenerator
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import kotlin.math.abs

@Service
class GameService(
    private val playerRepository: PlayerRepository,
    private val walletService: WalletService,
    private val randomNumberGenerator: RandomNumberGenerator,
) {

    fun placeBet(playerUsername: String, betAmount: Float, betNumber: Int): Bet {
        val player = retrievePlayerAndCheckBalance(playerUsername, betAmount)

        // place bet
        walletService.updateBalanceAndInsertTransaction(player, -betAmount, TransactionType.BET)

        val generatedNumber = randomNumberGenerator.generate()
        val winAmount = calculateWinAmount(betAmount, betNumber, generatedNumber)

        if (winAmount > 0) {
            walletService.updateBalanceAndInsertTransaction(player, winAmount, TransactionType.WIN)
        }
        val betResult = Bet(
            player = player,
            betAmount = betAmount,
            betNumber = betNumber,
            generatedNumber = generatedNumber,
            winAmount = winAmount,
        )
        player.bets.add(betResult)
        playerRepository.save(player)
//        Not ideal to return the entity directly
        return betResult
    }

    private fun calculateWinAmount(betAmount: Float, betNumber: Int, generatedNumber: Int): Float {
        return when (abs(generatedNumber - betNumber)) {
            0 -> betAmount * 10
            1 -> betAmount * 5
            2 -> betAmount * 0.5f
            else -> 0f
        }
    }

    private fun retrievePlayerAndCheckBalance(playerUsername: String, betAmount: Float): Player {
        val player: Player = playerRepository.findByUsername(playerUsername).orElseThrow {
            EntityNotFoundException("Player not found")
        }

        if (player.walletBalance.balance < betAmount) {
            throw NotEnoughFundsException("Insufficient wallet balance")
        }

        return player
    }

}

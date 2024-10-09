package com.game.betting.service

import com.game.betting.exception.UsernameNotAvailableException
import com.game.betting.model.Player
import com.game.betting.model.TransactionType
import com.game.betting.model.WalletBalance
import com.game.betting.repository.PlayerRepository
import org.springframework.stereotype.Service

@Service
open class PlayerService(
    private val playerRepository: PlayerRepository,
    private val walletService: WalletService
) {

    fun registerNewPlayer(name: String, surname: String, username: String): Player {
        if (usernameExists(username))
            throw UsernameNotAvailableException("This username [$username] is not available.")

        var newPlayer = Player()
        newPlayer.name = name
        newPlayer.surname = surname
        newPlayer.username = username
        newPlayer.walletBalance = WalletBalance(newPlayer.id, newPlayer, 0f)

        newPlayer = playerRepository.save(newPlayer)

        return walletService.updateBalanceAndInsertTransaction(
            newPlayer,
            1000f,
            TransactionType.DEPOSIT,
            "Initial Deposit"
        )
    }

    private fun usernameExists(username: String): Boolean {
        return playerRepository.existsByUsername(username)
    }

    fun getAllPlayers(): List<Player> {
        return playerRepository.findAll()
    }

}

package com.game.betting.service

import com.game.betting.model.Player
import com.game.betting.model.TransactionType
import com.game.betting.model.WalletTransaction
import com.game.betting.repository.WalletBalanceRepository
import com.game.betting.repository.WalletTransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class WalletService(
    private val walletTransactionRepository: WalletTransactionRepository,
    private val walletBalanceRepository: WalletBalanceRepository
) {

    @Transactional
    open fun updateBalanceAndInsertTransaction(
        player: Player,
        amount: Float,
        transactionType: TransactionType,
        description: String? = null
    ): Player {
        val walletTransaction = WalletTransaction(
            player = player,
            transactionAmount = amount,
            transactionType = transactionType,
            transactionDescription = description,
        )

        player.walletBalance.balance += amount
        player.walletTransactions.add(walletTransaction)

        walletBalanceRepository.save(player.walletBalance)
        walletTransactionRepository.save(walletTransaction)

        return player
    }
}
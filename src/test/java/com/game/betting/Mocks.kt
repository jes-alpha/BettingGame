package com.game.betting

import com.game.betting.dto.BetRequestDto
import com.game.betting.dto.PlayerRequestDto
import com.game.betting.model.*
import java.time.LocalDateTime

object Mocks {

    fun betRequestDtoMock(
        username: String = "username",
        betAmount: Float = 10f,
        betNumber: Int = 5
    ) = BetRequestDto(username, betAmount, betNumber)

    fun betRequestDtoInvalidMock(
    ) = BetRequestDto("username", 1f, -1)

    fun playerRequestDtoMock(
        name: String = "name",
        surname: String = "surname",
        username: String = "username",
    ) = PlayerRequestDto(name, surname, username)

    fun betMock(
        id: Long = 1,
        player: Player? = null,
        betAmount: Float = 10f,
        betNumber: Int = 5,
        generatedNumber: Int = 5,
        winAmount: Float = 100f,
        timestamp: LocalDateTime = LocalDateTime.now()
    ) = Bet(id, player, betAmount, betNumber, generatedNumber, winAmount, timestamp)

    fun playerMock(
        id: Long = 1,
        name: String = "name",
        surname: String = "surname",
        username: String = "username",
        walletBalance: WalletBalance = walletBalanceMock(),
        walletTransactions: MutableList<WalletTransaction> = mutableListOf(),
        bets: MutableList<Bet> = mutableListOf()
    ) = Player(id, name, surname, username, walletBalance, walletTransactions, bets)

    fun walletBalanceMock(
        id: Long = 1,
        player: Player? = null,
        balance: Float = 1000.0f,
    ) = WalletBalance(id, player, balance)

    fun walletTransactionMock(
        id: Long = 1,
        player: Player? = null,
        transactionAmount: Float = 100.0f,
        transactionType: TransactionType? = TransactionType.DEPOSIT,
        transactionDescription: String? = "Initial deposit",
        timestamp: LocalDateTime = LocalDateTime.now(),
    ) = WalletTransaction(id, player, transactionAmount, transactionType, transactionDescription, timestamp)
}
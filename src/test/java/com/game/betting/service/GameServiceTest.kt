package com.game.betting.service

import com.game.betting.Mocks.betRequestDtoMock
import com.game.betting.Mocks.playerMock
import com.game.betting.Mocks.walletBalanceMock
import com.game.betting.exception.NotEnoughFundsException
import com.game.betting.model.TransactionType
import com.game.betting.repository.PlayerRepository
import com.game.betting.util.RandomNumberGenerator
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import javax.persistence.EntityNotFoundException

@SpringBootTest
class GameServiceTest {

    @InjectMocks
    private lateinit var gameService: GameService

    @Mock
    private lateinit var playerRepository: PlayerRepository

    @Mock
    private lateinit var walletService: WalletService

    @Mock
    private lateinit var randomNumberGenerator: RandomNumberGenerator

    @Test
    fun `should place bet successfully`() {
        val betRequestDto = betRequestDtoMock(betAmount = 10f, betNumber = 5)
        val player = playerMock()
        val generatedNumber = 5
        val expectedWinAmount = 100f

        `when`(playerRepository.findByUsername(anyString())).thenReturn(Optional.of(player))
        `when`(playerRepository.save(player)).thenReturn(player)
        `when`(randomNumberGenerator.generate()).thenReturn(generatedNumber)

        val betResponse = gameService.placeBet(betRequestDto.username, betRequestDto.betAmount, betRequestDto.betNumber)

        Assertions.assertThat(betResponse.betAmount).isEqualTo(betRequestDto.betAmount)
        Assertions.assertThat(betResponse.betNumber).isEqualTo(betRequestDto.betNumber)
        Assertions.assertThat(betResponse.winAmount).isEqualTo(expectedWinAmount)

        verify(walletService, times(1)).updateBalanceAndInsertTransaction(
            player,
            -betRequestDto.betAmount,
            TransactionType.BET
        )

        verify(walletService, times(1)).updateBalanceAndInsertTransaction(
            player,
            expectedWinAmount,
            TransactionType.WIN
        )
        verify(playerRepository, times(1)).save(player)
    }

    @Test
    fun `should throw NotEnoughFundsException for low balance`() {
        val betRequestDto = betRequestDtoMock(betAmount = 10f)
        val player = playerMock(walletBalance = walletBalanceMock(balance = 5f))

        `when`(playerRepository.findByUsername(betRequestDto.username)).thenReturn(Optional.of(player))

        val exception = assertThrows<NotEnoughFundsException> {
            gameService.placeBet(betRequestDto.username, betRequestDto.betAmount, betRequestDto.betNumber)
        }

        Assertions.assertThat(exception.message).isEqualTo("Insufficient wallet balance")
        verify(walletService, never()).updateBalanceAndInsertTransaction(
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull()
        )
    }

    @Test
    fun `should throw EntityNotFoundException for nonexistent player`() {
        val betRequestDto = betRequestDtoMock()
        `when`(playerRepository.findByUsername(betRequestDto.username)).thenReturn(Optional.empty())

        val exception = assertThrows<EntityNotFoundException> {
            gameService.placeBet(betRequestDto.username, betRequestDto.betAmount, betRequestDto.betNumber)
        }

        Assertions.assertThat(exception.message).isEqualTo("Player not found")
        verify(walletService, never()).updateBalanceAndInsertTransaction(
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull()
        )
    }


    @Test
    fun `should calculate and validate win amount through placeBet method`() {
        val betRequestDto = betRequestDtoMock(username = "player1", betAmount = 10.0f, betNumber = 5)
        val player = playerMock(username = betRequestDto.username)

        `when`(playerRepository.findByUsername(betRequestDto.username)).thenReturn(Optional.of(player))
        `when`(playerRepository.save(player)).thenReturn(player)

        val testCases = mapOf(
            5 to 100.0f,
            6 to 50.0f,
            7 to 5.0f,
            8 to 0.0f
        )

        for ((generatedNumber, expectedWinAmount) in testCases) {
            `when`(randomNumberGenerator.generate()).thenReturn(generatedNumber)
            val betResponse =
                gameService.placeBet(betRequestDto.username, betRequestDto.betAmount, betRequestDto.betNumber)
            Assertions.assertThat(betResponse.winAmount).isEqualTo(expectedWinAmount)
        }
    }
}
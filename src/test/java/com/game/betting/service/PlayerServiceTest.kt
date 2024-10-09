package com.game.betting.service

import com.game.betting.Mocks.playerMock
import com.game.betting.Mocks.playerRequestDtoMock
import com.game.betting.exception.UsernameNotAvailableException
import com.game.betting.model.Player
import com.game.betting.repository.PlayerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PlayerServiceTest {

    @InjectMocks
    private lateinit var playerService: PlayerService

    @Mock
    private lateinit var playerRepository: PlayerRepository

    @Mock
    private lateinit var walletService: WalletService

    @Test
    fun `should register new player successfully`() {
        val playerRequestDto = playerRequestDtoMock()
        val newPlayer = playerMock()

        `when`(playerRepository.existsByUsername(anyOrNull())).thenReturn(false)
        `when`(playerRepository.save(anyOrNull())).thenReturn(newPlayer)
        `when`(
            walletService.updateBalanceAndInsertTransaction(
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
            )
        ).thenReturn(newPlayer)

        val registeredPlayer =
            playerService.registerNewPlayer(playerRequestDto.name, playerRequestDto.surname, playerRequestDto.username)

        assertThat(registeredPlayer.username).isEqualTo(playerRequestDto.username)
        verify(playerRepository, times(1)).save(any(Player::class.java))
        verify(walletService, times(1)).updateBalanceAndInsertTransaction(
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
        )
    }

    @Test
    fun `should throw UsernameNotAvailableException for existing username`() {
        val playerRequestDto = playerRequestDtoMock()

        `when`(playerRepository.existsByUsername(playerRequestDto.username)).thenReturn(true)

        val exception = assertThrows<UsernameNotAvailableException> {
            playerService.registerNewPlayer(playerRequestDto.name, playerRequestDto.surname, playerRequestDto.username)
        }

        assertThat(exception.message).isEqualTo("This username [${playerRequestDto.username}] is not available.")
        verify(playerRepository, never()).save(any(Player::class.java))
        verify(walletService, never()).updateBalanceAndInsertTransaction(
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
        )
    }

    @Test
    fun `should return all players`() {
        val players = listOf(playerMock(), playerMock(2, "otherUsername"))

        `when`(playerRepository.findAll()).thenReturn(players)

        val allPlayers = playerService.getAllPlayers()

        assertThat(allPlayers).hasSize(2)
        assertThat(allPlayers).containsAll(players)
        verify(playerRepository, times(1)).findAll()
    }
}
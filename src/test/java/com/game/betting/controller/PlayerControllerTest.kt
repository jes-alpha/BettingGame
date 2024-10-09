package com.game.betting.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.game.betting.Mocks.playerMock
import com.game.betting.Mocks.playerRequestDtoMock
import com.game.betting.model.Player
import com.game.betting.service.PlayerService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var playerService: PlayerService

    private val registerUrl = "/register"
    private val playersUrl = "/players"

    @Test
    fun `should register player successfully`() {
        val playerRequestDto = playerRequestDtoMock()
        val playerMock = playerMock()

        `when`(playerService.registerNewPlayer(anyString(), anyString(), anyString())).thenReturn(playerMock)

        val mvcResult: MvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post(registerUrl)
                .content(objectMapper.writeValueAsString(playerRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andReturn()

        val response: Player = objectMapper.readValue(mvcResult.response.contentAsString, Player::class.java)

        assertThat(response).usingRecursiveComparison().isEqualTo(playerMock)

        verify(playerService, times(1)).registerNewPlayer(
            playerRequestDto.name,
            playerRequestDto.surname,
            playerRequestDto.username
        )
    }

    @Test
    fun `should return error for invalid username`() {
        val playerRequestDto = playerRequestDtoMock(username = "invalid*username")

        val mvcResult: MvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post(registerUrl)
                .content(objectMapper.writeValueAsString(playerRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).contains("Username must be 3 to 30 characters long and can only contain letters, numbers, periods (.), underscores (_), and hyphens (-)")

        verify(playerService, never()).registerNewPlayer(
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
        )
    }


    @Test
    fun `should return error for missing name`() {
        val playerRequestDto = playerRequestDtoMock(name = "")

        val mvcResult: MvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post(registerUrl)
                .content(objectMapper.writeValueAsString(playerRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).contains("Name must not be blank")

        verify(playerService, never()).registerNewPlayer(
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
        )
    }

    @Test
    fun `should return error for empty body`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post(registerUrl)
                .content(objectMapper.writeValueAsString(""))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andReturn()

        verify(playerService, never()).registerNewPlayer(
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
        )
    }

    @Test
    fun `should get all players successfully`() {
        val player1 = playerMock(id = 1, username = "username1")
        val player2 = playerMock(id = 2, username = "username2")
        val playersList = listOf(player1, player2)

        `when`(playerService.getAllPlayers()).thenReturn(playersList)

        val mvcResult: MvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get(playersUrl)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val response: List<Player> =
            objectMapper.readValue(mvcResult.response.contentAsString, Array<Player>::class.java).toList()

        assertThat(response).usingRecursiveComparison().isEqualTo(playersList)

        verify(playerService, times(1)).getAllPlayers()
    }
}
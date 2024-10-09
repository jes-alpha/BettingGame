package com.game.betting.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.game.betting.Mocks.betMock
import com.game.betting.Mocks.betRequestDtoInvalidMock
import com.game.betting.Mocks.betRequestDtoMock
import com.game.betting.model.Bet
import com.game.betting.service.GameService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var gameService: GameService

    private val url = "/game/bet"

    @Test
    fun `should place bet successfully`() {
        val betRequestDto = betRequestDtoMock()
        val betResponseCheck = betMock()

        `when`(gameService.placeBet(anyString(), anyFloat(), anyInt()))
            .thenReturn(betResponseCheck)

        val mvcResult: MvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(betRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn()

        val response: Bet = objectMapper.readValue(mvcResult.response.contentAsString, Bet::class.java)

        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(betResponseCheck)

        verify(gameService, times(1))
            .placeBet(betRequestDto.username, betRequestDto.betAmount, betRequestDto.betNumber)
    }

    @Test
    @Throws(JsonProcessingException::class, Exception::class)
    fun `should fail if incorrect input`() {
        val betRequestDto = betRequestDtoInvalidMock()
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post(this.url).content(objectMapper.writeValueAsString(betRequestDto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn()
    }

    @Test
    @Throws(JsonProcessingException::class, Exception::class)
    fun `should fail if missing Parameters`() {
        val betRequestJsonString = ""
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post(this.url).content(betRequestJsonString)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn()
    }
}
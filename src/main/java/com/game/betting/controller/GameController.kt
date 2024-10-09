package com.game.betting.controller

import com.game.betting.dto.BetRequestDto
import com.game.betting.model.Bet
import com.game.betting.service.GameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class GameController(private val gameService: GameService) {

    @PostMapping("/bet")
    fun placeBet(@RequestBody @Valid betRequestDto: BetRequestDto): ResponseEntity<Bet> {
        val result = gameService.placeBet(betRequestDto.username, betRequestDto.betAmount, betRequestDto.betNumber)
        return ResponseEntity.ok(result)
    }
}
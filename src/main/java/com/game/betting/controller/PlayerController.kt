package com.game.betting.controller

import com.game.betting.dto.PlayerRequestDto
import com.game.betting.model.Player
import com.game.betting.service.PlayerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class PlayerController(private val playerService: PlayerService) {

    @PostMapping("/register")
    fun registerPlayer(@RequestBody @Valid playerRequestDto: PlayerRequestDto): ResponseEntity<Player> {
        val player =
            playerService.registerNewPlayer(playerRequestDto.name, playerRequestDto.surname, playerRequestDto.username)
        return ResponseEntity(player, HttpStatus.CREATED)
    }

    @GetMapping("/players")
    fun getAllPlayers(): ResponseEntity<List<Player>> {
        val players = playerService.getAllPlayers()
        return ResponseEntity(players, HttpStatus.OK)
    }

}
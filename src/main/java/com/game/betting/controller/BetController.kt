package com.game.betting.controller

import com.game.betting.dto.LeaderboardDto
import com.game.betting.model.Bet
import com.game.betting.service.BetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BetController(private val betService: BetService) {

    @GetMapping("/leaderboard")
    fun getLeaderboard(): ResponseEntity<List<LeaderboardDto>> {
        val leaderboard = betService.getLeaderboard()
        return ResponseEntity.ok(leaderboard)
    }

    @GetMapping("/bets")
    fun getAllBets(): ResponseEntity<List<Bet>> {
        val bets = betService.getAllBets()
        return ResponseEntity.ok(bets)
    }

//    @GetMapping("/leaderboard")
//    fun getLeaderboard(): ResponseEntity<Flux<LeaderboardDto>> {
//        val leaderboard = gameService.getLeaderboard()
//        return ResponseEntity.ok(leaderboard)
//    }
}
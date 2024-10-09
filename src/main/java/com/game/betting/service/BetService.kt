package com.game.betting.service

import com.game.betting.dto.LeaderboardDto
import com.game.betting.model.Bet
import com.game.betting.repository.BetRepository
import org.springframework.stereotype.Service

@Service
class BetService(private val betRepository: BetRepository) {

    fun getLeaderboard(): List<LeaderboardDto> {
        return betRepository.getLeaderBoard()
    }

    fun getAllBets(): List<Bet> {
        return betRepository.findAll()
    }

//    fun getLeaderboard(): Flux<LeaderboardDto> {
//        return betRepository.getLeaderBoard()
//    }
}
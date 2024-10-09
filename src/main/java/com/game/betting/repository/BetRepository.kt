package com.game.betting.repository

import com.game.betting.dto.LeaderboardDto
import com.game.betting.model.Bet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BetRepository : JpaRepository<Bet, Long> {
    @Query(
        """
            SELECT p.username AS username, SUM(b.winAmount) AS totalWinAmount 
            FROM Bet b 
            JOIN b.player p 
            GROUP BY p.username 
            ORDER BY totalWinAmount DESC
        """
    )
    fun getLeaderBoard(): List<LeaderboardDto>
//    fun getLeaderBoard(): Flux<LeaderboardDto>
}
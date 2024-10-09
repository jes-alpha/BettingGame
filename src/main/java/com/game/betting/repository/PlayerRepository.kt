package com.game.betting.repository

import com.game.betting.model.Player
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PlayerRepository : JpaRepository<Player, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): Optional<Player>
}
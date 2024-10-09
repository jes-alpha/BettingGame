package com.game.betting.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Bet(
    @Id
    @GeneratedValue(generator = "tsid")
    @GenericGenerator(
        name = "tsid",
        strategy = "io.hypersistence.utils.hibernate.id.TsidGenerator"
    )
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    @JsonBackReference
    val player: Player? = null,

    val betAmount: Float = 0f,
    val betNumber: Int = 0,
    val generatedNumber: Int? = null,
    val winAmount: Float = 0f,
    val timestamp: LocalDateTime = LocalDateTime.now(),
) {
    override fun toString(): String {
        return "Bet(id=$id, betAmount=$betAmount, betNumber=$betNumber, generatedNumber=$generatedNumber, winAmount=$winAmount, timestamp=$timestamp)"
    }
}


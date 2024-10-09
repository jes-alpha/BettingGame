package com.game.betting.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class WalletTransaction(
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
    var player: Player? = null,

    var transactionAmount: Float = 0f,
    var transactionType: TransactionType? = null,
    var transactionDescription: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now(),
) {
    override fun toString(): String {
        return "WalletTransaction(id=$id, transactionAmount=$transactionAmount, transactionType=$transactionType, transactionDescription=$transactionDescription, timestamp=$timestamp)"
    }
}

enum class TransactionType {
    DEPOSIT, WITHDRAW, BET, WIN,
}
package com.game.betting.model

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
class WalletBalance(
    @Id
    @Column(name = "player_id")
    var id: Long = 0,

    @OneToOne
    @MapsId
    @JoinColumn(name = "player_id")
    @JsonBackReference
    var player: Player? = null,

    var balance: Float = 0f,
) {
    override fun toString(): String {
        return "WalletBalance(balance=$balance)"
    }
}
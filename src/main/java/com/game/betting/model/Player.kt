package com.game.betting.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
class Player(
    @Id
    @GeneratedValue(generator = "tsid")
    @GenericGenerator(
        name = "tsid",
        strategy = "io.hypersistence.utils.hibernate.id.TsidGenerator"
    )
    val id: Long = 0,
    var name: String = "",
    var surname: String = "",

    @Column(unique = true)
    var username: String = "",

    @OneToOne(mappedBy = "player", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    var walletBalance: WalletBalance = WalletBalance(),

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonManagedReference
    var walletTransactions: MutableList<WalletTransaction> = mutableListOf(),

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonManagedReference
    var bets: MutableList<Bet> = mutableListOf(),
) {
    override fun toString(): String {
        return "Player(id=$id, name='$name', surname='$surname', username='$username', walletBalance=${walletBalance.balance}, walletTransactions=$walletTransactions, bets=$bets)"
    }
}


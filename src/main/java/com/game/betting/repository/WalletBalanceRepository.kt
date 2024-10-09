package com.game.betting.repository

import com.game.betting.model.WalletBalance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletBalanceRepository : JpaRepository<WalletBalance, Long> {
}
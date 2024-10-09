package com.game.betting.util

import org.springframework.stereotype.Component
import java.security.SecureRandom


interface RandomNumberGenerator {
    fun generate(): Int
}

@Component
class SecureRandomNumberGenerator : RandomNumberGenerator {
    private val secureRandom = SecureRandom()
    override fun generate(): Int {
        return secureRandom.nextInt(0, 10)
    }
}
package com.game.betting.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SecureRandomNumberGeneratorTest {

    private val randomNumberGenerator: RandomNumberGenerator = SecureRandomNumberGenerator()

    @Test
    fun `should generate number between 0 and 9 inclusive`() {
        for (i in 1..1000) {
            val number = randomNumberGenerator.generate()
            assertThat(number).isBetween(0, 9)
        }
    }

    @Test
    fun `should generate numbers that appear to be random`() {
        val numbers = mutableSetOf<Int>()

        for (i in 1..1000) {
            numbers.add(randomNumberGenerator.generate())
        }

        // Ensure that all possible outcomes (0-9) are present which indicates randomness
        for (i in 0..9) {
            assertThat(numbers).contains(i)
        }
    }
}
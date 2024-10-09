package com.game.betting.dto

import javax.validation.constraints.*

class BetRequestDto(
    @field:NotBlank(message = "Username must not be blank")
    val username: String,

    @field:NotNull(message = "Bet amount cannot be null")
    @field:Positive(message = "Bet number must be greater than 0")
    @field:Digits(integer = 10, fraction = 2)
    val betAmount: Float,

    @field:NotNull(message = "Bet number cannot be null")
    @field:Min(0, message = "Bet number must be at least 0")
    @field:Max(10, message = "Bet number must be at most 10")
    val betNumber: Int,
)
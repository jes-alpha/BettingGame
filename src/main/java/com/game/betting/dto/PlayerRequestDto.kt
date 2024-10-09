package com.game.betting.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class PlayerRequestDto(
    @field:NotBlank(message = "Name must not be blank")
    val name: String,

    @field:NotBlank(message = "Surname must not be blank")
    val surname: String,

    @field:NotBlank(message = "Username must not be blank")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9._-]{3,30}$",
        message = "Username must be 3 to 30 characters long and can only contain letters, numbers, periods (.), underscores (_), and hyphens (-)"
    )
    val username: String,
)
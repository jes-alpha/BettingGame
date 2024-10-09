package com.game.betting.dto

import org.springframework.http.HttpStatus

data class ErrorResponse(
    var status: HttpStatus,
    var message: String,
    var errors: List<String>,
)
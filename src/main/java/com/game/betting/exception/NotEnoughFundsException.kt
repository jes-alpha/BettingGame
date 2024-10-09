package com.game.betting.exception

class NotEnoughFundsException : RuntimeException {

    constructor(message: String?) : super(message)

    constructor(e: Exception) : super(e.message)
}

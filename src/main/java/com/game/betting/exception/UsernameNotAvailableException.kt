package com.game.betting.exception

public class UsernameNotAvailableException : RuntimeException {

    constructor() : super()
    constructor(message: String?) : super(message)

    constructor(e: Exception) : super(e.message)
}

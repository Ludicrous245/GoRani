package com.ludicrous245.core.data.variables

import dev.kord.core.Kord

object Config {
    const val version = "v0.42"

    var isDebug = true
    var isLoading = true

    lateinit var client: Kord

    lateinit var token: String
    lateinit var ytToken: String
    lateinit var prefix: String
    lateinit var owner: String
    lateinit var password: String

}
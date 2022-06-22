package com.ludicrous245.api.command

import dev.kord.core.entity.Message


abstract class Command(val description: String, val commandSide: CommandSide, val isDevCommand: Boolean, val childCommand: String) {

    abstract suspend fun run(content:String, message: Message, args:ArrayList<String>)

    enum class CommandSide{
        GUILD,
        DIRECT_MESSAGE,
        BOTH_SIDE
    }
}
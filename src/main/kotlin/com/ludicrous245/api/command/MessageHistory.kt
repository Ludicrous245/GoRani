package com.ludicrous245.api.command

import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Message

abstract class MessageHistory {
    abstract val id: Snowflake

    val history = ArrayList<Pair<String, String>>()

    suspend fun onUpdate(message: Message){
        val stamp = message.editedTimestamp!!

        history.add(Pair(stamp.toString(), message.content))
    }

    suspend fun onCreate(message: Message){
        val stamp = message.timestamp

        history.add(Pair(stamp.toString(), message.content))
    }

}
package com.ludicrous245.api.command.basic

import com.ludicrous245.api.command.MessageHistory
import com.ludicrous245.core.data.variables.Config
import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Message
import kotlin.collections.HashMap

object GoraniST {
    val messageLogs = HashMap<Snowflake, MessageHistory>()
    val messagePairKey = HashMap<Snowflake, Pair<String, String>>()
    val guildLogs = HashMap<Guild, GuildHistory>()

    suspend fun onUpdate(message: Message){
        println("key regened ${message.id}")
        messagePairKey[message.id] = Pair(message.author!!.tag, message.content)

        val history = searchHistory(message.id)

        history.onUpdate(message)
    }

    suspend fun onCreate(message: Message){
        println("key created ${message.id}")
        if(messagePairKey.size > 3000){
            messagePairKey.clear()
            println("로그 클리어")
        }

        messagePairKey[message.id] = Pair(message.author!!.tag, message.content)

        val history = searchHistory(message.id)
        history.onCreate(message)
    }

    fun onDelete(snowflake: Snowflake, guild: Guild){
        val history = searchHistory(guild)

        history.onDelete(snowflake)
    }

    fun searchHistory(id: Snowflake): MessageHistory{
        if(!messageLogs.containsKey(id)){
            messageLogs[id] = object : MessageHistory(){
                override val id = id
            }
        }

        return messageLogs[id]!!
    }

    fun searchHistory(guild: Guild): GuildHistory{
        if(!guildLogs.containsKey(guild)){
            guildLogs[guild] = object : GuildHistory(){
                override val guild = guild
            }
        }

        return guildLogs[guild]!!
    }
}
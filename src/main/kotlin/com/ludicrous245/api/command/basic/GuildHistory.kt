package com.ludicrous245.api.command.basic

import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Guild

abstract class GuildHistory {
    abstract val guild: Guild

    val history = ArrayList<Pair<String, String>>()

    fun onDelete(snowflake: Snowflake){
        println("deleted $snowflake")

        val content = GoraniST.messagePairKey[snowflake]!!
        history.add(content)
    }

}

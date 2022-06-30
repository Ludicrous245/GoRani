package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.data.variables.Config
import com.ludicrous245.core.data.variables.HL4Data
import com.ludicrous245.core.io.Console
import dev.kord.core.entity.Message
import dev.schlaubi.lavakord.kord.lavakord
import io.ktor.http.*

class Reconnect: Command("Lavalink 다시 연결", CommandSide.BOTH_SIDE, true, "") {
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        println("Try to Connect")

        val client = Config.client

        val lavalink = client.lavakord()
        lavalink.addNode(Url.invoke("ws://localhost:2333"), Config.password)

        HL4Data.lavalink = lavalink
    }

}
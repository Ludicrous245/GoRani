package com.ludicrous245.api.command.basic

import com.ludicrous245.api.command.CommandModule
import com.ludicrous245.api.command.basic.commands.*
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.GuildBehavior
import dev.kord.core.entity.Message


class BasicCommand : CommandModule() {
    override fun onLoad() {
        registerCommand(Test())
        registerCommand(Play())
        registerCommand(Queue())
        registerCommand(Skip())
        registerCommand(Stop())
        registerCommand(Loop())
        registerCommand(Pause())
        registerCommand(Speed())
        registerCommand(Help())
        registerCommand(Continue())
        registerCommand(Replay())
        registerCommand(Transmit())
        registerCommand(Remove())
        registerCommand(Enko())
        registerCommand(Reconnect())
        registerCommand(Restore())
    }

    override fun onUnload() {

    }

    override suspend fun onMessageCreate(message: Message) {
        if(!message.author!!.isBot){
            GoraniST.onCreate(message)
        }
    }

    override suspend fun onMessageDelete(snowflake: Snowflake, guild: GuildBehavior?) {
        GoraniST.onDelete(snowflake, guild!!.asGuild())
    }

    override suspend fun onMessageEdit(message: Message){
        GoraniST.onUpdate(message)
    }
}
package com.ludicrous245.api.command.basic

import com.ludicrous245.api.command.CommandModule
import com.ludicrous245.api.command.basic.commands.*
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.GuildBehavior
import dev.kord.core.entity.Message
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.MessageDeleteEvent
import dev.kord.core.event.message.MessageUpdateEvent
import dev.kord.core.on


class BasicCommand(override val client: Kord) : CommandModule(client) {
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

    override suspend fun eventLoader() {
        client.on<MessageCreateEvent> {
            if(!message.author!!.isBot){
                GoraniST.onCreate(message)
            }
        }

        client.on<MessageDeleteEvent> {
            GoraniST.onDelete(messageId, guild!!.asGuild())
        }

        client.on<MessageUpdateEvent> {
            GoraniST.onUpdate(message.asMessage())
        }
    }
}
package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.command.CommandStore
import com.ludicrous245.core.data.variables.Config
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.entity.Message

class Help : Command("도움말을 보여줍니다.", CommandSide.BOTH_SIDE, false, "") {
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        val commandsInStore = CommandStore.commands
        val commands = commandsInStore.toSortedMap()

        val embed = EmbedWrapper()
        embed.setTitle("도움말")

        for (commandName in commands.keys) {
            val command = commands[commandName]!!

            if(!command.isDevCommand) {
                if(command.childCommand != ""){
                    embed.addField("${Config.prefix}$commandName, ${Config.prefix}${command.childCommand}", command.description)
                }else{
                    embed.addField("${Config.prefix}$commandName", command.description)
                }
            }
        }

        embed.sendIt(message.channel, EmbedWrapper.EmbedType.NORMAL)
    }
}
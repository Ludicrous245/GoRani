package com.ludicrous245.core.command

import com.github.kimcore.inko.Inko.Companion.asEnglish
import com.ludicrous245.api.HandlerBase
import com.ludicrous245.api.command.Command
import com.ludicrous245.core.data.isBotAdmin
import com.ludicrous245.core.data.variables.Config
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message

class CommandHandler(val message: Message) : HandlerBase{

    override suspend fun handle(){

        if(message.author != null) {
            if (!message.author!!.isBot) {

                val commands = HashMap<String, Command>()

                commands.putAll(CommandStore.commands)
                commands.putAll(CommandStore.children)

                val args: ArrayList<String> = ArrayList(message.content.replace(Config.prefix, "").split(" "))

                val cmd = args.removeAt(0)

                val command = commands[cmd]

                if(command == null){

                    val firstCheck = commands[cmd.asEnglish]
                    val secondCheck = commands[cmd.lowercase()]
                    val thirdCheck = commands[cmd.asEnglish.lowercase()]

                    if(firstCheck != null){

                        runExecute(firstCheck, cmd, args)
                    }else if(secondCheck != null){

                        runExecute(secondCheck, cmd, args)
                    }else if(thirdCheck != null){

                        runExecute(thirdCheck, cmd, args)
                    }
                    else{
                        message.reply {
                            this.content = "없는 명령어입니다."
                        }
                    }
                }else{
                    runExecute(command, cmd, args)
                }
            }
        }
    }

    private suspend fun runExecute(command: Command, cmd: String, args:ArrayList<String>){
        if (command.isDevCommand) {

            if (message.author != null) {
                if (message.author!!.isBotAdmin()) {

                    execute(command, cmd, args)

                } else {

                    message.channel.createMessage("봇 관리자만 사용할 수 있습니다.")

                }

            }
        }else {

            execute(command, cmd, args)
        }
    }

    private suspend fun execute(command: Command, cmd: String, args:ArrayList<String>){
        if (command.commandSide == Command.CommandSide.DIRECT_MESSAGE) {

            if (message.getGuildOrNull() == null) {

                command.run(cmd, message, args)
            } else {
                message.channel.createMessage("DM에서만 사용할 수 있습니다.")
            }

        } else if (command.commandSide == Command.CommandSide.GUILD) {
            if (message.getGuildOrNull() != null) {

                command.run(cmd, message, args)
            } else {
                message.channel.createMessage("길드에서만 사용할 수 있습니다.")
            }
        } else {

            command.run(cmd, message, args)
        }
    }
}
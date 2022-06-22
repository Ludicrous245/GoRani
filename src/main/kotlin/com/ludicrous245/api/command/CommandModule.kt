package com.ludicrous245.api.command


import com.ludicrous245.core.command.CommandStore
import com.ludicrous245.core.io.Console
import dev.kord.core.behavior.MessageBehavior
import dev.kord.core.entity.Message

abstract class CommandModule {

    abstract fun onLoad()

    abstract fun onUnload()

    fun onMessageCreate(message: Message) {

    }

    fun onMessageDelete(message: Message){

    }

    fun onReaction(messageBehavior: MessageBehavior, message: Message){

    }

    fun registerCommand(command: Command){
        if(command.childCommand != ""){
            Console.printDebugMessage(command.childCommand)
            CommandStore.children[command.childCommand] = command
            Console.printPreDebugMessage("loadedChildCommand : ${command.childCommand}")
        }

        val name = command.javaClass.simpleName.lowercase()
        Console.printPreDebugMessage(name)
        CommandStore.commands[name] = command
        Console.printPreDebugMessage("loadedCommand : $name")
    }

}
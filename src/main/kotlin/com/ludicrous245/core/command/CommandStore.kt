package com.ludicrous245.core.command

import com.ludicrous245.api.command.Command
import com.ludicrous245.api.command.CommandModule
import kotlin.collections.HashMap

object CommandStore {
    val commandModules: MutableList<CommandModule> = ArrayList()

    val commands: HashMap<String, Command> = HashMap()
    val children: HashMap<String, Command> = HashMap()


}
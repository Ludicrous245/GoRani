package com.ludicrous245.api.command.basic

import com.ludicrous245.api.command.CommandModule
import com.ludicrous245.api.command.basic.commands.*


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
    }

    override fun onUnload() {

    }
}
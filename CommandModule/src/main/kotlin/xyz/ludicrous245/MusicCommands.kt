package xyz.ludicrous245

import com.ludicrous245.api.command.CommandModule
import xyz.ludicrous245.commands.ITest


class MusicCommands: CommandModule() {
    override fun onLoad() {
        registerCommand(ITest())
    }

    override fun onUnload() {

    }
}
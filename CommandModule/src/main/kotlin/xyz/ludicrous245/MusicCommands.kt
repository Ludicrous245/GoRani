package xyz.ludicrous245

import com.ludicrous245.api.command.CommandModule
import xyz.ludicrous245.commands.Minjar


class MusicCommands: CommandModule() {
    override fun onLoad() {
        registerCommand(Minjar())
    }

    override fun onUnload() {

    }
}
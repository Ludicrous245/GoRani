package xyz.ludicrous245.commands

import com.ludicrous245.api.command.Command
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message

class ITest : Command("실험용.", CommandSide.BOTH_SIDE, true, ""){
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        message.reply {
            this.content = "A"
        }
    }
}
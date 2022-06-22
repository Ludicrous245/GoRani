package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.entity.toHL4
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.entity.Message

class Loop: Command("현재 대기열을 반복합니다.", CommandSide.GUILD, false, "repeat") {
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        val member = message.getAuthorAsMember()!!.toHL4()

        val guild = member.member.guild.asGuild().toHL4()

        val embed = EmbedWrapper()
        embed.setTitle("반복재생")

        if(guild.isLoop) {

            guild.isLoop = false

            guild.garbage.clear()

            embed.setDescription("반복모드를 종료합니다.")

        }else{

            guild.isLoop = true

            embed.setDescription("반복모드를 활성화 합니다.")
        }

        embed.sendIt(message.channel, EmbedWrapper.EmbedType.SPECIAL)
    }

}

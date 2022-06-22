package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.entity.getPlayer
import com.ludicrous245.core.entity.toHL4
import dev.kord.core.entity.Message

class Continue: Command("채널을 이동시켜 재생이 멈췄을 경우 사용합니다.", CommandSide.GUILD, false, "") {
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        val member = message.getAuthorAsMember()!!.toHL4()

        val guild = member.member.guild.asGuild().toHL4()

        val player = guild.getPlayer()

        player.player.pause()
        player.player.unPause()
    }
}
package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.entity.getPlayer
import com.ludicrous245.core.entity.toHL4
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.entity.Message

class Pause: Command("재생중인 트랙을 일시정지합니다.", CommandSide.GUILD, false, "") {
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        val member = message.getAuthorAsMember()!!.toHL4()

        val guild = member.member.guild.asGuild().toHL4()

        val player = guild.getPlayer()

        player.player.pause()

        val embed = EmbedWrapper()
        embed.setTitle("일시정지")
        embed.setDescription("재생중인 음악을 일시정지 했습니다.")
        embed.sendIt(message.channel, EmbedWrapper.EmbedType.SPECIAL)
    }
}
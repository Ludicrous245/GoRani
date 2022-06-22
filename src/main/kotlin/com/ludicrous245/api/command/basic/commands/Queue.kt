package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.entity.getPlayer
import com.ludicrous245.core.entity.toHL4
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.entity.Message

class Queue : Command("대기열을 확인합니다.", CommandSide.GUILD, false, "") {
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        val member = message.getAuthorAsMember()!!.toHL4()

        val guild = member.member.guild.asGuild().toHL4()

        val player = guild.getPlayer()

        var index = 1

        var embedType = EmbedWrapper.EmbedType.NORMAL

        val embed = EmbedWrapper()
        embed.setTitle("대기열")

        if(player.queue.getSize() > 24){
            embed.setDescription("추가로, 표기되지 못한${guild.queue.getSize() - 24}개의 트랙이 재생 대기중.")
        }

        if(guild.isLoop) {
            embedType = EmbedWrapper.EmbedType.LOOP_STATE
            embed.setDescription("${guild.garbage.getSize()}개의 트랙이 반복 대기중.")

            if(player.queue.getSize() > 24){
                embed.setDescription("${guild.garbage.getSize()}개의 트랙이 반복 대기중 및 표기되지 못한 ${player.queue.getSize() - 24}개의 곡이 재생 대기중")
            }
        }

        embed.addField("현재 재생중인 곡", player.player.playingTrack!!.title)

        for (music in player.queue.elements()) {
            embed.addField("[$index] ${music.title}", "요청자: ${music.requester}", true)

            index++
        }

        embed.sendIt(message.channel, embedType)
    }
}
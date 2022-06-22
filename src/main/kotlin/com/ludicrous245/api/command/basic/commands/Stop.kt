package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.entity.getPlayer
import com.ludicrous245.core.entity.toHL4
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.entity.Message

class Stop : Command("모든 재생을 정지시킵니다.", CommandSide.GUILD, false, "clear"){
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {

        val member = message.getAuthorAsMember()!!.toHL4()

        val guild = member.member.guild.asGuild().toHL4()

        val player = guild.getPlayer()

        val track = guild.playingMusic

        if(track != null) {

            guild.playedMusic = track.clone()

            if(!player.queue.isEmpty()) {
                for (element in player.queue.elements()) {
                    player.bin.add(element.clone())
                }
            }

            if(!player.garbage.isEmpty()) {
                for (element in player.garbage.elements()) {
                    player.garbageBin.add(element.clone())
                }
            }

            player.queue.clear()
            player.garbage.clear()

            player.disconnect()
            player.player.stopTrack()
            player.player.unPause()

            guild.playingMusic = null

            if(content != "NOMESSAGE") {
                val embed = EmbedWrapper()
                embed.setTitle("트랙 정지됨")
                embed.setDescription("재생중인 트랙을 중단하고 대기열을 모두 비웠어요.")
                embed.sendIt(message.channel, EmbedWrapper.EmbedType.ALERT)
            }
        }
    }
}
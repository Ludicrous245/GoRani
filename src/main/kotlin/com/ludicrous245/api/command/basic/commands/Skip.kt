package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.entity.getPlayer
import com.ludicrous245.core.entity.toHL4
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message

class Skip : Command("재생중인 곡을 넘깁니다.", CommandSide.GUILD, false, ""){
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {

        val member = message.getAuthorAsMember()!!.toHL4()

        val guild = member.member.guild.asGuild().toHL4()

        val player = guild.getPlayer()

        if(player.player.playingTrack == null){

            message.reply {
                this.content = "넘길 수 있는 곡이 없습니다."
            }

            return
        }

        if(player.queue.isEmpty()) {
            if (!player.garbage.isEmpty()) {
                player.loop()

                return
            }
        }

        player.next()

    }

}
package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.entity.getPlayer
import com.ludicrous245.core.entity.toHL4
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message

class Replay: Command("이전에 비워졌던 대기열을 다시 불러옵니다.", CommandSide.GUILD, false, "") {
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        val member = message.getAuthorAsMember()!!.toHL4()

        val guild = member.member.guild.asGuild().toHL4()

        val player = guild.getPlayer()

        val playedMusic = guild.playedMusic

        if(guild.playingMusic != null){
            message.reply {
                this.content = "이미 무언가를 재생중입니다."
            }

            return
        }

        if (!member.isJoinedVoiceChannel()) {

            message.reply {
                this.content = "먼저, 음성 채널에 접속해주세요."
            }

            return
        }

        player.connect(member.getJoinedVoiceChannel()!!.id)

        if(playedMusic != null){
            player.queue.offer(playedMusic)

            if(guild.isLoop){
                player.loop()
            }else{
                player.next()
            }
        }

        if(guild.bin.isNotEmpty()){
            player.queue.offerAll(guild.bin)
        }

        if(guild.garbageBin.isNotEmpty()){
            player.garbage.offerAll(guild.garbageBin)
        }

        val embed = EmbedWrapper()
        embed.setTitle("다시 재생합니다.")
        embed.setDescription("전에 비웠던 대기열을 불러왔습니다.")
        embed.sendIt(message.channel, EmbedWrapper.EmbedType.SPECIAL)
    }

}
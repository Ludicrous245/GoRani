package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.data.variables.Config
import com.ludicrous245.core.entity.toHL4
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message

class Remove : Command("대기열에 있는 노래를 지웁니다.", CommandSide.GUILD, false, ""){
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {

        val member = message.getAuthorAsMember()!!.toHL4()

        val guild = member.member.guild.asGuild().toHL4()

        val index = args[0].toInt()

        if(args.size == 0){
            message.reply {
                this.content = "사용방법: ${Config.prefix}remove [번호]"
            }
        }

        if(guild.queue.getSize() > index) {
            val track = guild.queue.removeAt(index-1)

            val embed = EmbedWrapper()
            embed.setTitle("음악 제거")
            embed.setDescription("${index}에 있던 ${track.title}을 삭제했습니다.")
            embed.sendIt(message.channel, EmbedWrapper.EmbedType.NORMAL)
        }else{
            message.reply {
                this.content = "입력하신 숫자가 대기열보다 큽니다."
            }
        }
    }
}
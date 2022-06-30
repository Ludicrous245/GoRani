package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.api.command.basic.GoraniST
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.common.entity.MessageType
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.EmbedBuilder

class Restore: Command("메세지 로그를 확인합니다.", CommandSide.BOTH_SIDE, false, "") {
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        if(message.type == MessageType.Reply) {
            val repliedMessage = message.channel.getMessage(message.messageReference!!.data.id.value!!)

            val logs = GoraniST.searchHistory(repliedMessage.id)

            val embed = EmbedWrapper()

            embed.setTitle("메세지 수정 내역")

            if(logs.history.isEmpty()){
                embed.setDescription("조회된 로그가 없습니다.")
            }else {
                var i = 0
                for (log in logs.history) {
                    if (i == 0) {

                        embed.addField("[${log.first}] 기록 시작됨", "기록된 메세지: '${log.second}'")

                    } else {
                        val oldMessage = logs.history[i - 1].second

                        embed.addField("[${log.first}] 메세지 수정됨", "'$oldMessage' -> '${log.second}'")
                    }

                    i++
                }
            }

            embed.sendIt(message.channel, EmbedWrapper.EmbedType.SPECIAL)

        } else{

            val guild = message.getGuildOrNull()

            if(guild == null){
                message.reply {
                    this.content = "메세지 삭제 로그 조회는 길드에서만 가능합니다."
                }
            }else{

                val embed = EmbedWrapper()

                embed.setTitle("메세지 삭제 내역")

                val logs = GoraniST.searchHistory(message.getGuild())

                if(logs.history.isEmpty()){
                    embed.setDescription("조회된 로그가 없습니다.")
                }else{
                    for(log in logs.history){
                        embed.addField("'${log.second}'", "작성자: ${log.first}")
                    }
                }

                embed.sendIt(message.channel, EmbedWrapper.EmbedType.SPECIAL)

            }
        }
    }
}
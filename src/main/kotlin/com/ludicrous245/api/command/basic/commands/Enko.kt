package com.ludicrous245.api.command.basic.commands

import com.github.kimcore.inko.Inko.Companion.asKorean
import com.ludicrous245.api.command.Command
import dev.kord.common.entity.MessageType
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message

class Enko : Command("영타를 한국어로 번역합니다.", CommandSide.BOTH_SIDE, false, ""){
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        if(message.type == MessageType.Reply){
            val repliedMessage = message.channel.getMessage(message.messageReference!!.data.id.value!!).content

            val translated = repliedMessage.asKorean
            message.reply {
                this.content = translated
            }
        }else{
            message.reply {
                this.content = "번역 하고자 하는 메시지의 답장에 명령어를 입력해 주세요"
            }
        }
    }

}
package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.audio.youtube.YoutubeSearch
import com.ludicrous245.api.youtube.isURL
import com.ludicrous245.core.data.variables.HL4Data
import com.ludicrous245.core.entity.getPlayer
import com.ludicrous245.core.entity.toHL4
import com.ludicrous245.core.io.Console
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message

class Play : Command("음악을 재생합니다", CommandSide.GUILD, false, "p"){
    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {

        val rawMember = message.getAuthorAsMember()

        println(message.getAuthorAsMember())
        println(message.author)

        val member = rawMember!!.toHL4()

        val guild = member.member.guild.asGuild().toHL4()

        val player = guild.getPlayer()

        if(args.size != 0) {

            if (!member.isJoinedVoiceChannel()) {

                message.reply {
                    this.content = "먼저, 음성 채널에 접속해주세요."
                }

                return
            }

            player.connect(member.getJoinedVoiceChannel()!!.id)

            if (args[0].isURL()) {
                player.play(args[0], member, message)
            }else{
                Console.printDebugMessage("${message.content} is not an URL")

                if(member.searchData != null){
                    member.searchResponseMessage!!.delete()
                    member.searchResponseMessage = null

                    member.searchData = null
                }

                val search = YoutubeSearch()

                member.searchData = search.search(message)

                if(member.searchData == null){
                    message.reply {
                        this.content = "검색 결과가 없어요. 정확하게 입력했는지 다시 확인해주세요!"
                    }
                }

                var j = 0

                val embed = EmbedWrapper()
                embed.setTitle("검색 결과")

                for((i, searchData) in member.searchData!!.contents.withIndex()){
                    if(j == 5){
                        break
                    }

                    embed.addField("트랙 [${i+1}]", searchData.snippet.title)

                    j++
                }

                embed.setDescription("유튜브 검색 상위 ${j}개의 영상을 가져왔어요! 아래 이모지를 눌러 선택해주세요.")
                val searchResponseMessage = embed.sendIt(message.channel, EmbedWrapper.EmbedType.SPECIAL)

                HL4Data.messagePair[searchResponseMessage] = member.member

                member.searchResponseMessage = searchResponseMessage

                for(i in 0 until member.searchData!!.contents.size){
                    searchResponseMessage.addReaction(HL4Data.reactions[i])
                    println("added reaction: ${HL4Data.reactions[i]}")
                }

                searchResponseMessage.addReaction(HL4Data.reactions[5])

            }

        }else{
            if(player.player.paused){
                player.player.unPause()

                val embed = EmbedWrapper()
                embed.setTitle("일시정지")
                embed.setDescription("일시정지가 해제되었습니다.")
                embed.sendIt(message.channel, EmbedWrapper.EmbedType.SPECIAL)

                return
            }

            message.reply {
                this.content = "URL 또는 유튜브 동영상 제목이 필요합니다."
            }
        }

    }
}
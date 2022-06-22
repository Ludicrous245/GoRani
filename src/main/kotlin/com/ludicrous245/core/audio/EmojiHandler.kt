package com.ludicrous245.core.audio

import com.google.api.services.youtube.model.SearchResult
import com.ludicrous245.api.entity.HL4Member
import com.ludicrous245.api.HandlerBase
import com.ludicrous245.api.youtube.getURL
import com.ludicrous245.core.data.variables.HL4Data
import com.ludicrous245.core.entity.*
import com.ludicrous245.core.io.Console
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.entity.Message
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.entity.User

class EmojiHandler(val message: Message, val emoji: ReactionEmoji, val user: User) : HandlerBase {

    override suspend fun handle() {
        val reactions = HL4Data.reactions

        val member = message.getPairedMember()

        Console.printDebugMessage("Reaction with emoji $emoji")
        Console.printDebugMessage(HL4Data.messagePair)
        Console.printDebugMessage(member)

        if(member != null) {

            if(member.user.user == user) {

                Console.printDebugMessage("User Accepted")

                val searchData = member.searchData!!.contents

                if (member.isJoinedVoiceChannel()) {

                    Console.printDebugMessage("Member in voice channel")

                    when (emoji) {

                        reactions[0] -> {
                            play(searchData[0].getURL(), member, member.searchResponseMessage!!)

                            edit(searchData[0])
                            member.searchResponseMessage = null
                            member.searchData = null
                        }

                        reactions[1] -> {
                            play(searchData[1].getURL(), member, member.searchResponseMessage!!)

                            edit(searchData[1])
                            member.searchResponseMessage = null
                            member.searchData = null
                        }

                        reactions[2] -> {
                            play(searchData[2].getURL(), member, member.searchResponseMessage!!)

                            edit(searchData[2])
                            member.searchResponseMessage = null
                            member.searchData = null
                        }

                        reactions[3] -> {
                            play(searchData[3].getURL(), member, member.searchResponseMessage!!)

                            edit(searchData[3])
                            member.searchResponseMessage = null
                            member.searchData = null
                        }

                        reactions[4] -> {
                            play(searchData[4].getURL(), member, member.searchResponseMessage!!)

                            edit(searchData[4])
                            member.searchResponseMessage = null
                            member.searchData = null
                        }

                        reactions[5] -> {

                            member.searchResponseMessage = null
                            member.searchData = null

                            message.disconnectPair()

                            val embed = EmbedWrapper()
                            embed.setTitle("삭제되었습니다.")
                            embed.setDescription("기존 검색 결과가 삭제되었습니다.")

                            embed.editIt(message, EmbedWrapper.EmbedType.ALERT)
                        }

                        else -> {
                            return
                        }
                    }
                }
            }

            Console.printDebugMessage("Paired user is ${message.getPairedMember()!!.user} but interacted user is $user")
        }
    }

    private suspend fun edit(searchResult: SearchResult){
        val embed = EmbedWrapper()
        embed.setTitle("선택되었습니다.")
        embed.setDescription(searchResult.snippet.title)

        embed.editIt(message, EmbedWrapper.EmbedType.SPECIAL)

    }

    private suspend fun play(index: String, requester: HL4Member, message: Message){
        val guild = message.getGuild().toHL4()
        val player = guild.getPlayer()

        if (!requester.isJoinedVoiceChannel()) {

            message.channel.createMessage("먼저, 음성 채널에 접속해주세요.")

            return
        }

        player.play(index, requester, message)
        message.disconnectPair()
    }
}
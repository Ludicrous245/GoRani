package com.ludicrous245.api.entity

import com.ludicrous245.api.youtube.SearchData
import dev.kord.core.behavior.channel.BaseVoiceChannelBehavior
import dev.kord.core.entity.Member
import dev.kord.core.entity.Message

abstract class HL4Member {

    abstract val user: HL4User
    abstract val member:Member

    var searchData: SearchData? = null
    var searchResponseMessage:Message? = null

    suspend fun getJoinedVoiceChannel(): BaseVoiceChannelBehavior? {
        return member.getVoiceState().getChannelOrNull()
    }

    suspend fun isJoinedVoiceChannel(): Boolean{
        return getJoinedVoiceChannel() != null
    }

}
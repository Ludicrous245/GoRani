package com.ludicrous245.core.entity

import com.ludicrous245.api.entity.*
import com.ludicrous245.core.audio.TrackPlayer
import com.ludicrous245.core.data.variables.HL4Data
import com.ludicrous245.core.io.Console
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Member
import dev.kord.core.entity.Message
import dev.kord.core.entity.User
import dev.schlaubi.lavakord.audio.player.Track
import dev.schlaubi.lavakord.rest.TrackResponse

fun Guild.toHL4(): HL4Guild {

    val guilds = HL4Data.guilds
    val original = this

    if(guilds.containsKey(this)){
        return guilds[this]!!
    }

    val hl4Guild = object : HL4Guild(){
        override val guild: Guild
            get() = original
    }

    guilds[this] = hl4Guild

    return guilds[this]!!
}

fun User.toHL4(): HL4User {

    val user = this

    return object : HL4User() {
        override val user: User
            get() = user
        override val id: Long
            get() = user.id.value.toLong()
        override val tag: String
            get() = user.tag
    }
}

suspend fun Member.toHL4(): HL4Member {

    val members = HL4Data.members

    if(members.containsKey(this)){
        return members[this]!!
    }

    val member = this
    val user = this.asUser()

    val fbkMember = object : HL4Member(){
        override val user: HL4User
            get() = user.toHL4()
        override val member: Member
            get() = member
    }

    members[this] = fbkMember

    return members[this]!!

}

suspend fun TrackResponse.PartialTrack.translate(user: HL4User): HL4Music {
    val track = this.toTrack()
    val trackInfo = this.info

    return object : HL4Music() {
        override val requester: String
            get() = user.tag

        override val title: String
            get() = trackInfo.title

        override val length: String
            get() = trackInfo.length.toString()

        override val author: String
            get() = trackInfo.author

        override val url: String
            get() = trackInfo.uri

        override val audioTrack: Track
            get() = track
    }
}

fun HL4Guild.getPlayer(): TrackPlayer {
    val players = HL4Data.guildPlayer

    if(players.containsKey(this)){
        return players[this]!!
    }

    val player = TrackPlayer(this.link, this)

    players[this] = player

    return players[this]!!
}

suspend fun Message.getPairedMember(): HL4Member{
    return HL4Data.messagePair[this]!!.toHL4()
}

fun Message.pairWithMember(member: HL4Member){
    HL4Data.messagePair[this] = member.member
    Console.printDebugMessage("Paired message $this with $member")
}

fun Message.disconnectPair(){
    HL4Data.messagePair.remove(this)
}
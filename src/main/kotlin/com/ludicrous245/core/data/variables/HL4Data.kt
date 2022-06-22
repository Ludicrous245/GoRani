package com.ludicrous245.core.data.variables

import com.ludicrous245.core.audio.TrackPlayer
import com.ludicrous245.api.entity.HL4Guild
import com.ludicrous245.api.entity.HL4Member
import dev.kord.core.entity.*
import dev.schlaubi.lavakord.LavaKord

object HL4Data {
    val guilds = HashMap<Guild, HL4Guild>()
    val guildPlayer = HashMap<HL4Guild, TrackPlayer>()

    val messagePair = HashMap<Message, Member>()

    val members = HashMap<Member, HL4Member>()

    val reactions = ArrayList<ReactionEmoji>()

    lateinit var lavalink:LavaKord
}
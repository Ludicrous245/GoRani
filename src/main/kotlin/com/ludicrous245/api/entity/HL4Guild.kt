package com.ludicrous245.api.entity

import com.ludicrous245.core.data.ListDeque
import com.ludicrous245.core.data.variables.HL4Data
import dev.kord.core.entity.Guild
import dev.kord.voice.VoiceConnection
import dev.schlaubi.lavakord.kord.getLink

abstract class HL4Guild {
    abstract val guild:Guild

    var isLoop = false
    var volume = 50

    val queue = ListDeque<HL4Music>()
    val garbage = ListDeque<HL4Music>()
    var playingMusic: HL4Music? = null

    val bin = ArrayList<HL4Music>()
    val garbageBin = ArrayList<HL4Music>()
    var playedMusic: HL4Music? = null

    val link = guild.getLink(HL4Data.lavalink)

    var connection:VoiceConnection? = null

}
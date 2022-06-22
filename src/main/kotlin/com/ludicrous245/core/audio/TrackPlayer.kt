package com.ludicrous245.core.audio

import com.ludicrous245.api.entity.HL4Guild
import com.ludicrous245.api.entity.HL4Member
import com.ludicrous245.api.entity.HL4Music
import com.ludicrous245.core.data.repack
import com.ludicrous245.core.data.variables.Config
import com.ludicrous245.core.entity.*
import com.ludicrous245.core.io.Console
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Message
import dev.kord.core.entity.VoiceState
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.entity.channel.VoiceChannel
import dev.schlaubi.lavakord.audio.Link
import dev.schlaubi.lavakord.audio.TrackEndEvent
import dev.schlaubi.lavakord.audio.on
import dev.schlaubi.lavakord.kord.connectAudio
import dev.schlaubi.lavakord.rest.TrackResponse
import dev.schlaubi.lavakord.rest.loadItem
import kotlinx.coroutines.flow.toCollection

class TrackPlayer(private val lavalink: Link, private val guild: HL4Guild){

    val queue = guild.queue
    val player = lavalink.player
    val garbage = guild.garbage

    val bin = guild.bin
    val garbageBin = guild.garbageBin

    var where:Snowflake? = null

    private var channel: MessageChannel? = null

    init {
        player.on<TrackEndEvent> {
            val vChannel = Config.client.getChannelOf<VoiceChannel>(where!!)!!
            val voiceStates = ArrayList<VoiceState>()

            vChannel.voiceStates.run {
                toCollection(voiceStates)
            }

            if(voiceStates.isEmpty()){
                queue.clear()
                garbage.clear()

                player.stopTrack()
                player.unPause()

                guild.playingMusic = null

                disconnect()
            }

            if(reason.mayStartNext) {
                if(guild.isLoop){
                    loop()
                }else {
                    next()
                }
            }
        }
    }

    suspend fun connect(channel: Snowflake){
        if(lavalink.state != Link.State.CONNECTED && lavalink.state != Link.State.CONNECTING) {
            where = channel

            lavalink.connectAudio(channel)
            Console.printDebugMessage(lavalink.state)
        }
    }

    suspend fun disconnect(){
        if(lavalink.state == Link.State.CONNECTED || lavalink.state == Link.State.CONNECTING) {
            lavalink.disconnectAudio()
        }
    }

    suspend fun play(index: String, requester: HL4Member, message: Message) {
        this.channel = message.channel.asChannel()

        val item = lavalink.loadItem(index)

        connect(requester.getJoinedVoiceChannel()!!.id)

        when (item.loadType) {
            TrackResponse.LoadType.TRACK_LOADED -> {
                val music = item.track.translate(requester.user)

                queue(music)
            }

            TrackResponse.LoadType.PLAYLIST_LOADED -> {

                val playlist = item.tracks.repack()

                val compilation = ArrayList<HL4Music>()

                val selectedTrack = playlist.first().translate(requester.user)

                for (track in playlist) {
                    val music = track.translate(requester.user)
                    compilation.add(music)
                }

                compilation.remove(selectedTrack)

                queue(compilation)
            }

            TrackResponse.LoadType.SEARCH_RESULT -> {
                return
            }

            TrackResponse.LoadType.LOAD_FAILED -> {
                val embed = EmbedWrapper()
                embed.setTitle("문제 발생!")
                embed.addField("곡 정보를 불러오는데 실패했습니다.", "올바른 URL을 입력했는지 확인해주세요.")
                embed.sendIt(message.channel, EmbedWrapper.EmbedType.ALERT)
            }

            TrackResponse.LoadType.NO_MATCHES -> {

            }
        }
    }

    suspend fun next(){
        if(queue.isEmpty()){
            guild.playingMusic = null
            disconnect()

            return
        }

        start()

    }

    suspend fun loop(){
        if(queue.isEmpty()){
            garbage.offer(guild.playingMusic!!.clone())

            player.stopTrack()

            queue(garbage.elements())
            garbage.clear()

            return
        }

        garbage.offer(guild.playingMusic!!.clone())
        start()
    }

    fun setChannel(channel: TextChannel){
        this.channel = channel
    }

    private suspend fun queue(music: HL4Music){
        queue.offer(music)

        if(player.playingTrack == null){
            start()
        }else{
            sendPlayMessage(music, "대기열에 추가됨")
        }
    }

    private suspend fun queue(musics: Collection<HL4Music>){
        queue.offerAll(musics)

        if(player.playingTrack == null) {
            start()
        }
    }

    private suspend fun start(){

        val music = queue.poll()

        if(!guild.isLoop) {
            sendPlayMessage(music, "재생중")
        }

        guild.playingMusic = music
        player.playTrack(music.audioTrack)

    }

    private suspend fun sendPlayMessage(music: HL4Music, titleName:String){

        val embed = EmbedWrapper()
        embed.setTitle(titleName, music.url)
        embed.setDescription(music.title)

        embed.addField("요청자", music.requester, true)
        embed.addField("업로더", music.author, true)
        embed.addField("길이", "약 ${music.length.toInt()/1000/60} 분", true)

        embed.sendIt(channel!!, EmbedWrapper.EmbedType.NORMAL)

    }

}
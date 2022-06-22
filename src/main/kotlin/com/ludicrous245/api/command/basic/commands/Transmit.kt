package com.ludicrous245.api.command.basic.commands

import com.ludicrous245.api.command.Command
import com.ludicrous245.core.data.complete
import com.ludicrous245.core.data.variables.Config
import com.ludicrous245.core.entity.getPlayer
import com.ludicrous245.core.entity.toHL4
import com.ludicrous245.core.io.Console
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.behavior.channel.TopGuildChannelBehavior
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Message
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.entity.channel.VoiceChannel
import kotlinx.coroutines.flow.toCollection

class Transmit: Command("현재 길드에서 재생중인 대기열을 다른 길드로 전송합니다.", CommandSide.GUILD, false, "") {

    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {

        //arg check

        if(args.size == 0){
            message.reply {
                this.content = "사용방법: ${Config.prefix}transmit 길드 이름/음성 채널 이름/메세지 채널 이름"
            }

            return
        }

        //Pre Set

        val member = message.getAuthorAsMember()!!.toHL4()

        val guild = member.member.getGuild().toHL4()

        val player = guild.getPlayer()

        if(player.player.playingTrack == null){

            message.reply {
                this.content = "재생중인 곡이 없습니다."
            }

            return
        }

        //Target Set

        val client = Config.client
        val guilds = ArrayList<Guild>()
        val fullString = args.complete().split("/")

        val guildName = fullString[0]
        Console.printDebugMessage(guildName)

        val channelName = fullString[1]
        Console.printDebugMessage(channelName)

        val textChannelName = fullString[2]
        Console.printDebugMessage(textChannelName)

        var exportGuild: Guild? = null

        //Target Set: Err

        if(fullString.size < 3){
            message.reply {
                this.content = "사용방법: ${Config.prefix}transmit 길드 이름/음성 채널 이름/메세지 채널 이름"
            }

            return
        }

        //Task 1

        client.guilds.run {
            toCollection(guilds)
        }

        val embed = EmbedWrapper()
        embed.setTitle("대기열 전송")
        embed.setDescription("길드 조회중입니다. (5/1)")
        val searchMessage = embed.sendIt(message.channel, EmbedWrapper.EmbedType.SPECIAL)

        for(checkUpGuild in guilds){
            Console.printDebugMessage("${checkUpGuild.name} : $guildName")

            if(checkUpGuild.name == guildName){
                exportGuild = checkUpGuild
                break
            }
        }

        //Task 1: Err

        if(exportGuild == null){
            embed.setDescription("해당 길드를 찾을 수 없습니다.")
            embed.editIt(searchMessage, EmbedWrapper.EmbedType.ALERT)
            return
        }

        //Task 2

        val targetGuild = exportGuild.toHL4()

        embed.setDescription("해당 길드의 대기열이 비어있는지 조회중입니다. (5/2)")
        val isEmptyQueue = embed.editIt(searchMessage, EmbedWrapper.EmbedType.SPECIAL)

        //Task 2: Err

        if(targetGuild.playingMusic != null){
            embed.setDescription("해당 길드는 이미 음악을 재생중입니다.")
            embed.editIt(searchMessage, EmbedWrapper.EmbedType.ALERT)
        }

        //Task 3

        embed.setDescription("음성 채널 조회중입니다. (5/3)")
        val secondSearch = embed.editIt(isEmptyQueue, EmbedWrapper.EmbedType.SPECIAL)

        val channels = ArrayList<TopGuildChannelBehavior>()
        var voiceChannel: VoiceChannel? = null

        exportGuild.channels.run {
            toCollection(channels)
        }

        for(channel in channels){
            if(channel is VoiceChannel){

                Console.printDebugMessage("${channel.name} : $channelName")

                if(channel.name == channelName){
                    voiceChannel = channel
                    break
                }

            }
        }

        //Task 3: Err

        if(voiceChannel == null){
            embed.setDescription("해당 음성 채널을 찾을 수 없습니다.")
            embed.editIt(secondSearch, EmbedWrapper.EmbedType.ALERT)
            return
        }

        //Task 4

        var textChannel: TextChannel? = null

        for(channel in channels){
            if(channel is TextChannel){

                Console.printDebugMessage("${channel.name} : $textChannelName")

                if(channel.name == textChannelName){
                    textChannel = channel
                    break
                }

            }
        }

        //Task 4: Err

        if(textChannel == null){
            embed.setDescription("해당 메세지 채널을 찾을 수 없습니다.")
            embed.editIt(secondSearch, EmbedWrapper.EmbedType.ALERT)
            return
        }

        //Task 5

        embed.setDescription("대기열 전송중입니다. (5/5)")
        val exportMessage = embed.editIt(searchMessage, EmbedWrapper.EmbedType.SPECIAL)

        val queue = guild.queue
        val garbage = guild.garbage
        val track = guild.playingMusic!!.clone()

        val bin = guild.bin
        val garbageBin = guild.garbageBin
        val playedTrack = guild.playedMusic

        //Export
        val targetPlayer = targetGuild.getPlayer()
        targetPlayer.setChannel(textChannel)

        targetPlayer.connect(voiceChannel.id)

        targetGuild.isLoop = guild.isLoop

        targetGuild.queue.offer(track)

        if(!queue.isEmpty()){
            targetGuild.queue.offerAll(queue.elements())
        }

        if(!garbage.isEmpty()) {
            targetGuild.queue.offerAll(garbage.elements())
        }

        if(playedTrack != null){
            targetGuild.playedMusic = playedTrack
        }

        if(bin.isNotEmpty()){
            targetGuild.bin.addAll(bin)
        }

        if(garbageBin.isNotEmpty()){
            targetGuild.garbageBin.addAll(garbageBin)
        }

        //finalState

        Stop().run("NOMESSAGE", message, args)

        if(targetGuild.isLoop){
            targetPlayer.loop()
        }else{
            targetPlayer.next()
        }

        targetPlayer.player.pause()

        embed.setDescription("전송이 완료되었습니다. 일시정지를 해제하고, 음악을 즐겨주세요!")
        embed.sendIt(message.channel, EmbedWrapper.EmbedType.NORMAL)

    }

}
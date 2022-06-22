package com.ludicrous245.api.command.basic.commands

import com.github.kimcore.josa.Josa.josa
import com.ludicrous245.api.command.Command
import com.ludicrous245.core.entity.getPlayer
import com.ludicrous245.core.entity.toHL4
import com.ludicrous245.core.io.EmbedWrapper
import dev.kord.core.entity.Message
import dev.schlaubi.lavakord.audio.player.applyFilters
import dev.schlaubi.lavakord.audio.player.timescale

class Speed: Command("음악의 속도를 조절합니다.", CommandSide.GUILD, false, ""){

    override suspend fun run(content: String, message: Message, args: ArrayList<String>) {
        if(args.size == 0){
            val embed = EmbedWrapper()
            embed.setTitle("속도 조절")
            embed.addField("달팽이", "약 0.2배속", true)
            embed.addField("거북이", "약 0.5배속", true)
            embed.addField("느림", "약 0.75배속", true)
            embed.addField("보통", "1배속", true)
            embed.addField("빠름", "약 1.25배속", true)
            embed.addField("매우빠름", "약 1.5배속", true)
            embed.addField("매우매우빠름", "약 2배속", true)
            embed.addField("하나님맙소사", "약 4배속", true)
            embed.sendIt(message.channel, EmbedWrapper.EmbedType.NORMAL)
        }

        var option = SpeedType.NORMAL

        when (args[0]) {
            "달팽이" -> {
                option = SpeedType.SNAIL
            }

            "거북이" -> {
                option = SpeedType.TURTLE
            }

            "느림" -> {
                option = SpeedType.SLOW
            }

            "보통" -> {
                option = SpeedType.NORMAL
            }

            "빠름" -> {
                option = SpeedType.FAST
            }

            "매우빠름" -> {
                option = SpeedType.EXTRA_FAST
            }

            "매우매우빠름" -> {
                option = SpeedType.ZOOM
            }

            "하나님맙소사" -> {
                option = SpeedType.EXTRA_ZOOM
            }

            else -> {
                val embed = EmbedWrapper()
                embed.setTitle("잘못된 속도 옵션입니다.")
                embed.sendIt(message.channel, EmbedWrapper.EmbedType.ALERT)
                return
            }
        }

        val member = message.getAuthorAsMember()!!.toHL4()

        val guild = member.member.guild.asGuild().toHL4()

        val player = guild.getPlayer()

        player.player.applyFilters {
            timescale {
                speed = option.data
            }
        }

        val embed = EmbedWrapper()
        embed.setTitle("속도 변경됨")
        embed.addField("재생 속도가 ${args[0].josa("로/으로")} 변경되었습니다.", "변경되는데 시간이 약간 소요될 수 있습니다.")
        embed.sendIt(message.channel, EmbedWrapper.EmbedType.SPECIAL)

    }

    enum class SpeedType(val data: Float) {
        SNAIL(0.2f),
        TURTLE(0.5f),
        SLOW(0.75f),
        NORMAL(1.0f),
        FAST(1.25f),
        EXTRA_FAST(1.5f),
        ZOOM(2.0f),
        EXTRA_ZOOM(4.0f)
    }
}
package com.ludicrous245.core.io

import com.ludicrous245.core.data.variables.Config
import dev.kord.common.Color
import dev.kord.core.behavior.MessageBehavior
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.behavior.edit
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.modify.embed
import io.ktor.http.*

class EmbedWrapper {
    //field
    private val a = ArrayList<Pair<String, String>>()
    private val ab = ArrayList<Pair<String, String>>()

    //title
    private var b = ""
    private var bb = ""

    //description
    private var c = ""

    //rawEmbed
    private lateinit var embed: EmbedBuilder

    fun setTitle(title: String){
        b = title
    }

    fun setTitle(title: String, url: String){
        b = title
        bb = url
    }

    fun setDescription(description: String){
        c = description
    }

    fun addField(var1: String, var2: String){
        a.add(Pair(var1, var2))
    }

    fun addField(var1: String, var2: String, inLine: Boolean){
        if(inLine){
            ab.add(Pair(var1, var2))
        }else{
            a.add(Pair(var1, var2))
        }
    }

    private fun complete(type: EmbedType){
        embed = EmbedBuilder().apply {
            title = b
            url = bb

            description = c

            for(aa in a){
                field {
                    name = aa.first
                    value = aa.second
                    inline = false
                }
            }

            for(aa in ab){
                field {
                    name = aa.first
                    value = aa.second
                    inline = true
                }
            }

            color = type.data

            footer {
                text = "Go-RaniBot(${Config.version}) Copyright© Ludicrous245 All Rights Reserved"
            }
        }

        Console.printDebugMessage("Completed")

    }

    suspend fun sendIt(channel: MessageChannelBehavior, type: EmbedType): Message {
        complete(type)

        val message = channel.createEmbed {
            title = embed.title
            url = embed.url
            description = embed.description
            fields = embed.fields
            footer = embed.footer
            color = embed.color
        }

        println("sended: $message")

        return message
    }

    suspend fun editIt(message: MessageBehavior, type: EmbedType): Message{
        complete(type)

        return message.edit {
            embed {
                title = embed.title
                url = embed.url
                description = embed.description
                fields = embed.fields
                footer = embed.footer
                color = embed.color
            }
        }
    }

    enum class EmbedType(val data: Color) {
        NORMAL(Color(0x0099ff)),
        SPECIAL(Color(0xf5f5f5)),
        ALERT(Color(0xEF426F)),
        LOOP_STATE(Color(0xfcf765))
    }
}
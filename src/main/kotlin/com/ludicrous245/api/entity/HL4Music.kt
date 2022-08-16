package com.ludicrous245.api.entity

import dev.schlaubi.lavakord.audio.player.Track
import kotlin.time.ExperimentalTime

abstract class HL4Music{
    abstract val requester:String
    abstract val title:String
    abstract val length:String
    abstract val author:String
    abstract val url:String

    abstract val audioTrack:Track
    abstract val isCloned:Boolean

    @OptIn(ExperimentalTime::class)
    fun clone(): HL4Music {
        val clonedRequester = requester
        val clonedTitle = title
        val clonedLength = length
        val clonedAuthor = author
        val clonedURL = url

        val clonedAudioTrack = audioTrack.copy()

        return object : HL4Music(){
            override val requester: String
                get() = clonedRequester

            override val title: String
                get() = clonedTitle

            override val length: String
                get() = clonedLength

            override val author: String
                get() = clonedAuthor

            override val url: String
                get() = clonedURL

            override val audioTrack: Track
                get() = clonedAudioTrack

            override val isCloned: Boolean
                get() = true
        }

    }
}
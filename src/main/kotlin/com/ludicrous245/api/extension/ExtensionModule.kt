package com.ludicrous245.api.extension

import dev.kord.core.Kord
import dev.kord.core.on

abstract class ExtensionModule(open val client: Kord) {

    //EmojiHandler(event.message.asMessage(), event.emoji, event.user.asUser()).handle()

    abstract fun onLoad()

    abstract fun onUnload()

    abstract suspend fun eventLoader()
}
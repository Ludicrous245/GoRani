package com.ludicrous245

import com.google.gson.JsonObject
import com.ludicrous245.api.command.basic.BasicCommand
import com.ludicrous245.core.data.file.FileParser
import com.ludicrous245.core.command.CommandHandler
import com.ludicrous245.core.command.CommandStore
import com.ludicrous245.core.data.DataManager
import com.ludicrous245.core.data.file.FileManager
import com.ludicrous245.core.data.variables.Config
import com.ludicrous245.core.audio.EmojiHandler
import com.ludicrous245.core.command.loader.CommandFileDescription
import com.ludicrous245.core.data.variables.HL4Data
import dev.kord.common.entity.ActivityType
import dev.kord.common.entity.DiscordBotActivity
import dev.kord.common.entity.PresenceStatus
import dev.kord.core.Kord
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.MessageDeleteEvent
import dev.kord.core.event.message.MessageUpdateEvent
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.on
import dev.kord.gateway.*
import dev.schlaubi.lavakord.kord.lavakord
import io.ktor.http.*
import java.io.File
import kotlin.system.exitProcess

@OptIn(PrivilegedIntent::class)
suspend fun main(){
    dataFolderInit()

    CommandStore.commandModules.add(BasicCommand())

    for(cm in CommandStore.commandModules){
        cm.onLoad()
    }

    val config = FileParser.readFileAsJSON("./Data/System/system.json")!!
    val configInfo = config.get("config") as JsonObject

    if(configInfo["token"].asString == ""){
        println("Please put token in system.json")
        exitProcess(0)
    }

    if(configInfo["ownerID"].asString == ""){
        println("Please put ownerID in system.json")
        exitProcess(0)
    }

    Config.token = configInfo["token"].asString
    Config.ytToken = configInfo["yt-token"].asString
    Config.password = configInfo["password"].asString
    Config.prefix = configInfo["prefix"].asString
    Config.owner = configInfo["ownerID"].asString
    Config.isDebug = configInfo["isDebug"].asBoolean

    Runtime.getRuntime().addShutdownHook(object:Thread(){
        override fun run() {

            for(cm in CommandStore.commandModules){
                cm.onUnload()
            }

        }
    })

    Config.client = Kord(Config.token)

    val client = Config.client

    val reactions = HL4Data.reactions

    reactions.add(ReactionEmoji.Unicode("1️⃣"))
    reactions.add(ReactionEmoji.Unicode("2️⃣"))
    reactions.add(ReactionEmoji.Unicode("3️⃣"))
    reactions.add(ReactionEmoji.Unicode("4️⃣"))
    reactions.add(ReactionEmoji.Unicode("5️⃣"))
    reactions.add(ReactionEmoji.Unicode("❎"))

    println("Try to Connect")

    val lavalink = client.lavakord()

    lavalink.addNode(Url.invoke("ws://localhost:2333"), Config.password)
    HL4Data.lavalink = lavalink

    client.on<ReactionAddEvent> {

        if(!user.asUser().isBot){
            EmojiHandler(message.asMessage(), emoji, user.asUser()).handle()
        }
    }

    client.on<MessageUpdateEvent>{

        for (cm in CommandStore.commandModules) {
            cm.onMessageEdit(message.asMessage())
        }
    }

    client.on<MessageDeleteEvent>{

        for (cm in CommandStore.commandModules) {
            cm.onMessageDelete(messageId, guild)
        }

    }

    client.on<MessageCreateEvent> {

        for(cm in CommandStore.commandModules){
            cm.onMessageCreate(message)
        }

        if(message.content.startsWith(Config.prefix)){

            CommandHandler(message).handle()
        }

    }

    Config.isLoading = false

    client.login(){
        presence = DiscordPresence(PresenceStatus.Idle, false, null, DiscordBotActivity("(${Config.version})베타 테스트중", ActivityType.Game))
        intents += Intent.MessageContent
        intents += Intent.GuildMessageReactions
    }

}

internal fun loadCommandModules(){
    val descriptions = loadCommandDescriptions()

}

fun loadCommandDescriptions(): List<Pair<File, CommandFileDescription>>{
    return ArrayList()
}

fun dataFolderInit(){
    DataManager.createJsonDB("info")
    FileManager.createDirectory("./Data/Default")

    FileManager.createDirectory("./Commands")

    if(FileParser.readFileAsJSON("./Data/System/system.json") == null){

        DataManager.createJsonDB("system", "System")

        val systemObject = JsonObject()
        systemObject.addProperty("token", "")
        systemObject.addProperty("yt-token", "")
        systemObject.addProperty("password", "")
        systemObject.addProperty("ownerID", "")
        systemObject.addProperty("isDebug", true)
        systemObject.addProperty("prefix", "~")

        DataManager.addJsonData("system", "System","config", systemObject)
    }
}
package com.ludicrous245

import com.google.gson.JsonObject
import com.ludicrous245.api.command.basic.BasicCommand
import com.ludicrous245.core.audio.EmojiHandler
import com.ludicrous245.core.command.CommandHandler
import com.ludicrous245.core.command.CommandStore
import com.ludicrous245.core.command.loader.CommandFileDescription
import com.ludicrous245.core.command.loader.CommandLoader
import com.ludicrous245.core.data.DataManager
import com.ludicrous245.core.data.complete
import com.ludicrous245.core.data.file.FileManager
import com.ludicrous245.core.data.file.FileParser
import com.ludicrous245.core.data.variables.Config
import com.ludicrous245.core.data.variables.HL4Data
import com.ludicrous245.core.io.Console
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
import dev.kord.gateway.DiscordPresence
import dev.kord.gateway.Intent
import dev.kord.gateway.MessageReactionAdd
import dev.kord.gateway.PrivilegedIntent
import dev.schlaubi.lavakord.kord.lavakord
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.logging.Log
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.jar.JarFile
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

/**
 * Init And Boot
 */

private val commandLoader = CommandLoader()
private var location = 1

@OptIn(PrivilegedIntent::class)
suspend fun main(){
    //startInit
    println("Start Initializer")

    loadCommands()

    println(commandLoader.classes)

    dataFolderInit()

    //Load Info
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

    //Store Info

    Config.token = configInfo["token"].asString
    Config.ytToken = configInfo["yt-token"].asString
    Config.password = configInfo["password"].asString
    Config.prefix = configInfo["prefix"].asString
    Config.owner = configInfo["ownerID"].asString
    Config.isDebug = configInfo["isDebug"].asBoolean

    sendLoadMessage("Loaded Bot Default Info")

    Config.client = Kord(Config.token)

    val client = Config.client

    sendLoadMessage("Create Client")

    //DefaultCommandModule
    CommandStore.commandModules.add(BasicCommand(client))

    //CommandModuleLoad
    for(cm in CommandStore.commandModules){
        cm.onLoad()
    }

    sendLoadMessage("Register Commands")

    //OnDisable
    Runtime.getRuntime().addShutdownHook(object:Thread(){
        override fun run() {

            for(cm in CommandStore.commandModules){
                cm.onUnload()
            }

        }
    })

    sendLoadMessage("Set Shutdown Hook")

    //Init Reactions

    val reactions = HL4Data.reactions

    reactions.add(ReactionEmoji.Unicode("1️⃣"))
    reactions.add(ReactionEmoji.Unicode("2️⃣"))
    reactions.add(ReactionEmoji.Unicode("3️⃣"))
    reactions.add(ReactionEmoji.Unicode("4️⃣"))
    reactions.add(ReactionEmoji.Unicode("5️⃣"))
    reactions.add(ReactionEmoji.Unicode("❎"))

    sendLoadMessage("Init Bot Reactions")

    //lavakord

    val lavalink = client.lavakord()

    lavalink.addNode(Url.invoke("ws://localhost:2333"), Config.password)
    HL4Data.lavalink = lavalink


    sendLoadMessage("Connect lavakord")

    //default require vents

    for(cm in CommandStore.commandModules){
        cm.eventLoader()
    }

    client.on<ReactionAddEvent> {

        if(!user.asUser().isBot){
            EmojiHandler(message.asMessage(), emoji, user.asUser()).handle()
        }
    }

    client.on<MessageCreateEvent> {

        if(message.content.startsWith(Config.prefix)){

            CommandHandler(message).handle()
        }

    }

    sendLoadMessage("Register Events")

    //finishInit and boot

    sendLoadMessage("Wrap up Initializing Process...")
    Config.isLoading = false


    sendLoadMessage("Going to boot Hold on!")

    client.login(){
        presence = DiscordPresence(PresenceStatus.Idle, false, null, DiscordBotActivity("(${Config.version})베타 테스트중", ActivityType.Game))
        intents += Intent.MessageContent
        intents += Intent.GuildMessageReactions
    }

}

/**
 * CommandModuleLoad
 */

suspend fun loadCommands(){
    val descriptions = loadCommandModuleDescription()

    for((file, description) in descriptions){
        commandLoader.load(file, description)
    }
}

internal fun loadCommandFiles(): Array<File>{
    val commandFolder = File("./Commands")

    commandFolder.mkdirs()
    return commandFolder.listFiles { file -> !file.isDirectory && file.name.endsWith(".jar") }
        ?: return emptyArray()
}


internal suspend fun loadCommandModuleDescription(): List<Pair<File, CommandFileDescription>>{
    val arrayList = ArrayList<Pair<File, CommandFileDescription>>()

    val abilityFiles = loadCommandFiles()

    for(file in abilityFiles){
        arrayList.add(Pair(file, file.getCommandDescription()))
    }

    return arrayList
}

private suspend fun File.getCommandDescription(): CommandFileDescription {
    val commandFile = this
    val desc = JsonObject()

    withContext(Dispatchers.IO) {
        JarFile(commandFile).use { jar ->
            jar.getJarEntry("Module.json")?.let { entry ->
                jar.getInputStream(entry).bufferedReader(StandardCharsets.UTF_8).use { reader ->
                    val context = reader.readLines()
                        .complete()
                        .removePrefix("{")
                        .removeSuffix("}")
                        .replace(" ", "")
                        .replace("\"", "")
                        .split(",")

                    for(t in context){
                        val prop = t.split(":")

                        desc.addProperty(prop[0], prop[1])
                    }
                }
            }
        }
    }

    return CommandFileDescription(desc)
}

/**
 * DataFolder
 */

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

fun sendLoadMessage(string: String){
    println("[$location] $string")
    location ++
}
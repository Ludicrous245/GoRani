package com.ludicrous245.core.command.loader

import com.google.gson.JsonObject

class CommandFileDescription(info: JsonObject) {
    val group: String = requireNotNull(info.get("group").asString) { "gruop is not defined" }

    val name: String = requireNotNull(info.get("name").asString) { "name is not defined" }

    val main: String = requireNotNull(info.get("main").asString) { "main is not defined" }
}
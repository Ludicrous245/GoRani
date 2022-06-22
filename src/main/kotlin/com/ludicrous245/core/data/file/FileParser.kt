package com.ludicrous245.core.data.file

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ludicrous245.core.data.repack
import com.ludicrous245.core.io.Console
import java.io.File
import java.io.FileReader

object FileParser {
    fun readFileAsJSON(dir:String):JsonObject?{
        val adir = File(dir)

        Console.printDebugMessage(dir)

        var obj:JsonObject? = null

        if(adir.exists()){

            if(adir.isFile){

                Console.printDebugMessage("$dir is a file")

                    try {
                        val reader = FileReader(dir)

                        val parser = JsonParser()

                        obj = parser.parse(reader) as JsonObject

                        Console.printDebugMessage(obj)

                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

        }

        return obj

    }

    fun readFileAsString(dir:String):String{
        val dirs = dir.split("/").repack()

        val adir = File(dir)

        val fileName = dirs.last()

        var text = ""

        if(adir.exists()){

            if(adir.isFile){

                if(fileName.contains(".json")) {

                    val reader = FileReader(dir)

                    text = reader.readText()


                }

            }

        }

        return text

    }

}

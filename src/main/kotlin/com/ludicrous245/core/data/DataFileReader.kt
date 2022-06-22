package com.ludicrous245.core.data

import com.ludicrous245.core.data.file.FileParser
import com.ludicrous245.core.data.variables.Path
import java.io.File

object DataFileReader {

    fun readDataDir(){
        val dir = File("./Data/User/")
        val files = dir.listFiles()

        if(files != null && files.isNotEmpty()) {

            for (file in files) {

                val filename = file.name.replace(".json", "")

                println("./Data/User/${file.name}")
                println(File("./Data/User/${file.name}").isFile)

                val data = FileParser.readFileAsJSON("./Data/User/${file.name}")!!

                Path.userData[filename] = data
            }
        }
    }
}
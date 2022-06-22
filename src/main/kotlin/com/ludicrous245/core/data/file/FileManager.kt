package com.ludicrous245.core.data.file

import com.ludicrous245.core.data.repack
import com.ludicrous245.core.io.Console
import java.io.File
import java.io.FileWriter

object FileManager {
    fun createDirectory(dir:String):Boolean{
        val adir = dir.split("/").repack()

        adir.remove(".")

        if(adir.size > 1){
            adir.remove(adir.last())
        }

        Console.printDebugMessage(adir)

        var counter = 0

        var bdir = ""

        for(a in adir){
            bdir += "${a}/"

            if(bdir == "./"){
                continue

            }else {
                val cdir = File(bdir)

                if(!cdir.exists()){

                    if(!cdir.isDirectory){

                        cdir.mkdir()
                        counter++
                        Console.printDebugMessage("createDir: $cdir")

                    }else{
                        continue
                    }
                }else{
                    continue
                }

            }
        }

        if(counter == 0){

            return false
        }

        return true
    }

    fun createFile(dir:String):Boolean{

        createDirectory(dir)

        val ddir = File(dir)

        if(!ddir.exists()){

            if(!ddir.isFile){

                ddir.createNewFile()
                Console.printDebugMessage("createFile: $ddir")

            }else{

                Console.printDebugMessage("$dir is already file")
                return false
            }
        }else{

            Console.printDebugMessage("$dir is already exists")
            return false
        }

        return true
    }

    fun removeFile(dir:String):Boolean{

        val ddir = File(dir)

        if(ddir.exists()){

            if(ddir.isFile){

                ddir.delete()

            }else{

                Console.printDebugMessage("$dir is not file")
                return false
            }
        }else{

            Console.printDebugMessage("$dir is not exists")
            return false
        }

        return true
    }

    fun writeFile(file:String, text:String){

        createFile(file)

        val writer = FileWriter(file)
        writer.write(text)

        Console.printDebugMessage("다음 위치에 작성함: $file")
        Console.printDebugMessage("다음 내용을 작성함: $text")

        writer.flush()
        writer.close()

    }
}
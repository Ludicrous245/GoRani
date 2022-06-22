package com.ludicrous245.core.io

import com.ludicrous245.core.data.variables.Config

object Console {
    fun printDebugMessage(a:Any?):Boolean{
        if(Config.isDebug && !Config.isLoading){
            println(a)
            return true
        }

        return false
    }

    fun printPreDebugMessage(a:Any?){
        if(Config.isDebug) {
            println(a)
        }
    }

    fun log(log: Any?){
        println("Logged info: $log")
    }
}
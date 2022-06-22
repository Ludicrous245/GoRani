package com.ludicrous245.core.data

import dev.kord.core.entity.User

fun <T : Any> Collection<T>.repack(): ArrayList<T>{

    val a = ArrayList<T>()

    for(b in this){

        a.add(b)

    }

    return a
}

fun Collection<String>.complete():String {
    var syntax = ""

    for(arg in this){
        syntax = if(syntax == ""){
            arg
        }else {
            "$syntax $arg"
        }
    }

    return syntax
}

fun User.isBotAdmin():Boolean{
    return true
}
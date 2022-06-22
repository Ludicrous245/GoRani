package com.ludicrous245.api.youtube

import com.google.api.services.youtube.model.SearchResult
import java.lang.Exception
import java.net.URL

fun SearchResult.getURL(): String{
    return "https://www.youtube.com/watch?v=${this.id.videoId}"
}

fun String.isURL(): Boolean{
    return try{
        URL(this)

        true
    }catch (e: Exception){
        false
    }
}
package com.ludicrous245.core.audio.youtube

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import com.google.api.services.youtube.model.SearchResult
import com.ludicrous245.api.youtube.SearchData
import com.ludicrous245.core.data.complete
import com.ludicrous245.core.data.repack
import com.ludicrous245.core.data.variables.Config
import dev.kord.core.entity.Message

class YoutubeSearch {
    var youtube: YouTube

    init {
        youtube = YouTube.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            JacksonFactory.getDefaultInstance(),
            HttpRequestInitializer { fun initialize(requset: HttpRequest) {} }
        ).setApplicationName("FUBUKI").build()
    }

    fun search(message: Message): SearchData?{
        val args: ArrayList<String> = ArrayList(message.content.replace(Config.prefix, "").split(" "))
        args.removeAt(0)

        val searchList = ArrayList<String>()
        val typeList = ArrayList<String>()

        searchList.add("id")
        searchList.add("snippet")
        typeList.add("video")

        val search: YouTube.Search.List = youtube.search().list(searchList)

        search.key = Config.ytToken
        search.q = args.complete()
        search.type = typeList
        search.fields = "items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)"
        search.maxResults = 5

        val response: SearchListResponse = search.execute()

        if(response.items.isNullOrEmpty()){
            return null
        }

        return object: SearchData {
            override val message: Message
                get() = message

            override val contents: ArrayList<SearchResult>
                get() = response.items.repack()
        }
    }
}
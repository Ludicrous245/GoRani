package com.ludicrous245.api.youtube

import com.google.api.services.youtube.model.SearchResult
import dev.kord.core.entity.Message


interface SearchData {
    val message:Message
    val contents:ArrayList<SearchResult>
}
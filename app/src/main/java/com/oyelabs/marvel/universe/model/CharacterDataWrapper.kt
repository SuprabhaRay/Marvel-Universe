package com.oyelabs.marvel.universe.model
import java.util.*
import kotlin.collections.ArrayList
data class CharacterDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val data: CharacterDataContainer?,
    val etag: String
)
data class CharacterDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: ArrayList<Character>?
)
data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val modified: Date,
    val resourceURI: String,
    val urls: ArrayList<Url>,
    val thumbnail: Image,
    val comics: ComicList,
    val stories: ComicList,
    val events: ComicList,
    val series: ComicList
)
data class Url(
    val type: String,
    val url: String
)
data class Image(
    val path: String,
    val extension: String
)
data class ComicList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: ArrayList<ComicSummary>
)
data class ComicSummary(
    val resourceUri: String,
    val name: String,
    val type: String?
)
package com.perlu_dilindungi.data

import com.squareup.moshi.Json

/**
 * The data class for NewsData response object.
 */
data class News(
    var title: String,
    var link: List<String>,
    var guid: String,
    var pubDate: String,
    var description: NewsDescription,
    var enclosure: NewsEnclosure
)

data class NewsDescription(
    @Json(name = "__cdata")
    val cdata: String,
)


data class NewsEnclosure(
    @Json(name = "_url")
    val url: String,
    @Json(name = "_length")
    val length: String,
    @Json(name = "_type")
    val type: String,
)
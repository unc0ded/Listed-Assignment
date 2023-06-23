package com.unc0ded.listedapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LinkDetail(
    val title: String,
    @Json(name = "smart_link")
    val smartLink: String,
    @Json(name = "web_link")
    val webLink: String,
    @Json(name = "total_clicks")
    val totalClicks: Int,
    @Json(name = "times_ago")
    val timesAgo: String,
    @Json(name = "original_image")
    val originalImage: String,
    @Json(name = "created_at")
    val createdAt: String,
    val app: String
)

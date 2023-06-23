package com.unc0ded.listedapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.json.JSONObject

@JsonClass(generateAdapter = true)
data class HistoricalData(
    @Json(name = "recent_links")
    val recentLinks: List<LinkDetail>,
    @Json(name = "top_links")
    val topLinks: List<LinkDetail>,
    @Json(name = "overall_url_chart")
    val overallUrlChart: Map<String, Int>
)

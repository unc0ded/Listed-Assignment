package com.unc0ded.listedapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DashboardResponse(
    @Json(name = "total_links")
    val totalLinks: Int,
    @Json(name = "total_clicks")
    val totalClicks: Int,
    @Json(name = "today_clicks")
    val todayClicks: Int,
    @Json(name = "top_source")
    val topSource: String,
    @Json(name = "top_location")
    val topLocation: String,
    val startTime: String,
    @Json(name = "support_whatsapp_number")
    val whatsappNumber: String,
    val data: HistoricalData
)
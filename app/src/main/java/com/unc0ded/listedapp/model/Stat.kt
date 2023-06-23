package com.unc0ded.listedapp.model

data class Stat(
    val name: String,
    val value: String,
    val type: StatType
)

enum class StatType {
    TOTAL_CLICKS,
    TODAY_CLICKS,
    TOTAL_LINKS,
    TOP_LOCATION,
    TOP_SOURCE,
    BEST_TIME
}

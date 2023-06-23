package com.unc0ded.listedapp.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class DatesXAxisValueFormatter(private val datesList: List<String>) :
    ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        val position = value.roundToInt()
        return if (position < datesList.size)
            LocalDate.parse(datesList[position]).format(DateTimeFormatter.ofPattern("dd MMM"))
        else ""
    }
}

class ClicksYAxisValueFormatter() :
    ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        val clicks = value.roundToInt()
        return if (clicks >= 0)
            clicks.toString()
        else ""
    }
}
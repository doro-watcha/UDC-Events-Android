package com.goddoro.common.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateUtil {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREAN)
    private val dateFormat2 = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
    fun getToday() : String  {

        val today = Calendar.getInstance().time

        return dateFormat.format(today)

    }
    fun parseToYMD(src: Calendar, separator: String? = "-"): String {
        val year = src[Calendar.YEAR]
        val month = src[Calendar.MONTH] + 1
        val date = src[Calendar.DATE]
        return String.format("%04d", year) + separator + String.format(
            "%02d",
            month
        ) + separator + String.format("%02d", date)
    }


    fun parseString(raw: String): Calendar {
        val date = dateFormat.parse(raw)

        return Calendar.getInstance().apply { time = date }
    }
    fun calculateDiff(startDate: String, endDate: String) : Int {

        val startDateInMillis = parseString(startDate).timeInMillis
        val endDateInMillis = parseString(endDate).timeInMillis

        val diff = endDateInMillis - startDateInMillis

        return TimeUnit.MILLISECONDS.toDays(diff).toInt()

    }

    fun changeDateFormat(raw: String) : String {

        val date = dateFormat.parse(raw)
        return parseToYMD(date.toCalendar())
    }

    private fun Date.toCalendar(): Calendar {
        val cal = Calendar.getInstance()
        cal.time = this
        return cal
    }
}
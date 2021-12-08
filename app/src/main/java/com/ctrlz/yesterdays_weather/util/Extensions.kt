package com.ctrlz.yesterdays_weather.util

import java.text.SimpleDateFormat
import java.util.*

fun Int.toDate(): Date {
    val milli = this * 1000  // Second to Millis
    return Date(milli.toLong())
}

fun Date.getDateFormat() = SimpleDateFormat("yyyy-MM-dd").format(this)

fun Date.getSecondTimestamp() = (time / 1000).toInt()

// yesterday -> delta = -1
fun Date.moveDay(delta: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, delta)
    return calendar.time
}
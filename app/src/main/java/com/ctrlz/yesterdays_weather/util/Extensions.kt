package com.ctrlz.yesterdays_weather.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate() = Date(this * 1000)   // Second to Millis

fun Date.getDateFormat(): String = SimpleDateFormat("yyyy-MM-dd").format(this)

fun Date.getSecondTimestamp(): Long = time / 1000

// yesterday -> delta = -1
fun Date.moveDay(delta: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, delta)
    return calendar.time
}
package com.ctrlz.yesterdays_weather.data

import com.google.gson.annotations.SerializedName

// https://openweathermap.org/api/one-call-api#history

data class HistoricalWeatherData(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    @SerializedName("current")
    val dataPointWeather: DataPointWeather,
    val hourly: List<Hourly>
)
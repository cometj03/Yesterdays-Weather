package com.ctrlz.yesterdays_weather.data

// TODO: 다시하기

data class HistoricalWeather(
    val message: String,
    val cod: String,
    val city_id: Int,
    val calctime: Double,
    val cnt: Int,
    val list: List<abc>
)

data class abc (
    val dt: Int,    // Time of data calculation, unix, UTC
    val wind: Wind,
    val clouds: Clouds,
    val rain: Rain,
    val snow: Snow,
    val main: MainWeather,
    val weather: List<WeatherDescription>
)
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

// Components
data class DataPointWeather(
    val dt: Long,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val wind_gust: Double,
    val weather: List<WeatherDescription>
)

data class Hourly(
    val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,  // 이슬점
    val uvi: Double,    // ㅎㅎ..
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val wind_gust: Double,
    val weather: List<WeatherDescription>
)
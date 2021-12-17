package com.ctrlz.yesterdays_weather.data

import com.google.gson.annotations.SerializedName

data class WeatherDescription(
    val id: Int,        // https://gist.github.com/choipd/e73201a4653a5e56e830
    val main: String,
    val description: String,
    val icon: String    // https://openweathermap.org/weather-conditions
    // icon img: https://openweathermap.org/img/wn/{icon}@2x.png
)

data class Rain(
    @SerializedName("1h")
    val one_hour: Double,
    @SerializedName("3h")
    val three_hours: Double
)

data class Snow(
    @SerializedName("1h")
    val one_hour: Double,
    @SerializedName("3h")
    val three_hours: Double
)

data class DataPointWeather(
    val dt: Long,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,      // hPa
    val humidity: Int,      // %
    val dew_point: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double, // m/s
    val wind_deg: Int,      // Wind direction, degrees (meteorological)
    val wind_gust: Double,  // Wind gust(돌풍), m/s
    val weather: List<WeatherDescription>
)

data class Hourly(
    val dt: Long,   // Forecast: starts at 7:00 AM | Historical: starts at 00:00 AM
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,      // hPa
    val humidity: Int,      // %
    val dew_point: Double,  // 이슬점
    val uvi: Double,        // Double ㅎㅎ..
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val wind_gust: Double,
    val weather: List<WeatherDescription>,
    val pop: Double,    // Probability of precipitation (only forecast)
    val rain: Rain,
    val snow: Snow
)
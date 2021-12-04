package com.ctrlz.yesterdays_weather.data

import com.google.gson.annotations.SerializedName

data class CurrentWeatherData(
    val coord: Coord,
    val weather: List<WeatherDescription>,
    val base: String,
    val main: MainWeather,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val rain: Rain,
    val snow: Snow,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,        // city id
    val name: String,   // city name
    val cod: Int
)

// Components
data class Coord(
    val lon: Double,    // Intitude, 경도
    val lat: Double     // latitude, 위도
)

data class WeatherDescription(
    val id: Int,        // https://gist.github.com/choipd/e73201a4653a5e56e830
    val main: String,
    val description: String,
    val icon: String    // https://openweathermap.org/weather-conditions
    // icon img: https://openweathermap.org/img/wn/{icon}@2x.png
)

data class MainWeather(
    val temp: Double,       // Kelvin
    val feels_like: Double, // 체감 온도
    val pressure: Int,      // hPa
    val humidity: Int,      // %
    val temp_max: Double,
    val temp_min: Double,
    val sea_level: Int,     // pressure on the sea level, hPa
    val grnd_level: Int     // pressure on the ground level, hPa
)

data class Wind(
    val speed: Double,  // m/s
    val deg: Int,       // Wind direction, degrees (meteorological)
    val gust: Double    // Wind gust(돌풍), m/s
)

data class Clouds(
    val all: Int    // cloudiness, %
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

data class Sys(
    val type: Int,
    val id: Int,
    val message: String,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)
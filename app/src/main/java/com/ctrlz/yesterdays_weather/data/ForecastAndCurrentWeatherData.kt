package com.ctrlz.yesterdays_weather.data

data class ForecastAndCurrentWeatherData(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: Current,
    val hourly: List<Hourly>
)

data class Current(
    val dt: Long,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val feels_like: Double, // 체감 온도
    val pressure: Int,      // hPa
    val humidity: Int,      // %
    val dew_point: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double,
    val wind_gust: Double,
    val wind_deg: Int,
    val weather: List<WeatherDescription>
)
package com.ctrlz.yesterdays_weather

import retrofit2.Response

interface WeatherApi {
    fun getWeather(): Response<List<Weather>>
}
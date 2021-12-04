package com.ctrlz.yesterdays_weather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val WEATHER_BASE_URL = "https://api.openweathermap.org/"

    val currentWeatherService: CurrentWeatherService by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrentWeatherService::class.java)
    }

    val historicalWeatherService: HistoricalWeatherService by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HistoricalWeatherService::class.java)
    }
}
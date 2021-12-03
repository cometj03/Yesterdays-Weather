package com.ctrlz.yesterdays_weather.network

import com.ctrlz.yesterdays_weather.util.API_KEY
import com.ctrlz.yesterdays_weather.data.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") q: String,  // City name
        @Query("appid") apiKey: String = API_KEY,
        @Query("lang") lang: String = "kr"
    ): Response<Weather>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lon") longitude: Double,    // 경도
        @Query("lat") latitude: Double,     // 위도
        @Query("appid") apiKey: String = API_KEY,
        @Query("lang") lang: String = "kr"
    ): Response<Weather>
}
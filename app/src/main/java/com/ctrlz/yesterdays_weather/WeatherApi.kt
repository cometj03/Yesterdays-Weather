package com.ctrlz.yesterdays_weather

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") q: String,  // City name
        @Query("appid") apiKey: String = API_KEY
    ): Response<Weather>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lon") longitude: Double,    // 경도
        @Query("lat") latitude: Double,     // 위도
        @Query("appid") apiKey: String = API_KEY
    ): Response<Weather>
}
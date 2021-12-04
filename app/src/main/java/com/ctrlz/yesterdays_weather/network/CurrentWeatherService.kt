package com.ctrlz.yesterdays_weather.network

import com.ctrlz.yesterdays_weather.util.API_KEY
import com.ctrlz.yesterdays_weather.data.CurrentWeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface CurrentWeatherService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") q: String,  // City name
        @Query("appid") apiKey: String = API_KEY,
        @Query("lang") lang: String = "kr",
        @Query("units") units: String = "metric"
    ): Response<CurrentWeatherData>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("lang") lang: String = "kr",
        @Query("units") units: String = "metric"
    ): Response<CurrentWeatherData>

}
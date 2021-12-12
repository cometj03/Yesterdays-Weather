package com.ctrlz.yesterdays_weather.network

import com.ctrlz.yesterdays_weather.data.CurrentWeatherData
import com.ctrlz.yesterdays_weather.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double, // 위도
        @Query("lon") longitude: Double,// 경도
        @Query("appid") apiKey: String = API_KEY,
        @Query("lang") lang: String = "kr",
        @Query("units") units: String = "metric"
    ): Response<CurrentWeatherData>
}
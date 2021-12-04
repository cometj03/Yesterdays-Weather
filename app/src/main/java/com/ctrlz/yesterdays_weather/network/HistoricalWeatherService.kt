package com.ctrlz.yesterdays_weather.network

import com.ctrlz.yesterdays_weather.data.HistoricalWeatherData
import com.ctrlz.yesterdays_weather.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoricalWeatherService {

    @GET("data/2.5/onecall/timemachine")
    suspend fun getBeforeWeather(
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("dt") dataTime: Int,
        @Query("appid") apiKey: String = API_KEY,
        @Query("lang") lang: String = "kr",
        @Query("units") units: String = "metric"
    ): Response<HistoricalWeatherData>

}
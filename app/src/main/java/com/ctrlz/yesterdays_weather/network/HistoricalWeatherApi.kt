package com.ctrlz.yesterdays_weather.network

import com.ctrlz.yesterdays_weather.data.HistoricalWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoricalWeatherApi {

    @GET("data/2.5/onecall/timemachine")
    suspend fun getBeforeWeather(
        @Query("q") q: String,
        @Query("units") units: String = "metric"
    ): Response<HistoricalWeather>

}
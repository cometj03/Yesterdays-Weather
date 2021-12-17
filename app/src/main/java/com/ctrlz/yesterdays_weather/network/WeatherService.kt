package com.ctrlz.yesterdays_weather.network

import com.ctrlz.yesterdays_weather.data.ForecastAndCurrentWeatherData
import com.ctrlz.yesterdays_weather.data.HistoricalWeatherData
import com.ctrlz.yesterdays_weather.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/onecall/timemachine")
    suspend fun getHistoricalWeather(
        @Query("lat") latitude: Double,     // 위도
        @Query("lon") longitude: Double,    // 경도
        @Query("dt") dataTime: Long,
        @Query("appid") apiKey: String = API_KEY,
        @Query("lang") lang: String = "kr",
        @Query("units") units: String = "metric"
    ): Response<HistoricalWeatherData>

    @GET("data/2.5/onecall")
    suspend fun getCurrentAndForecastWeather(
        @Query("lat") latitude: Double,     // 위도
        @Query("lon") longitude: Double,    // 경도
        @Query("exclude") exclude: String = "minutely,daily,alerts",
        @Query("appid") apiKey: String = API_KEY,
        @Query("lang") lang: String = "kr",
        @Query("units") units: String = "metric"
    ): Response<ForecastAndCurrentWeatherData>
}
package com.ctrlz.yesterdays_weather.ui

import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.ctrlz.yesterdays_weather.data.CurrentWeatherData
import com.ctrlz.yesterdays_weather.data.HistoricalWeatherData
import com.ctrlz.yesterdays_weather.databinding.ActivityMainBinding
import com.ctrlz.yesterdays_weather.network.RetrofitInstance
import com.ctrlz.yesterdays_weather.util.*
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

   private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            val location = getCurrentLocation()
            val result = safeApiCall(Dispatchers.IO) {
                val yesterdayTime = Date().moveDay(-1).getSecondTimestamp()
                RetrofitInstance.historicalWeatherService.getBeforeWeather(location.latitude, location.longitude, yesterdayTime)
            }
            val response = result.getOrElse {
                exceptionHandle(it)
                return@launchWhenCreated
            }

            if (response.isSuccessful) {
                setHistoricalWeather(response.body())
            } else {
                val jsonObject = JSONObject(response.errorBody()?.string() ?: "")
                val errorMessage = jsonObject.getString("message")

                binding.tvYesterday.text = "Response not successful. code: ${response.code()}\n\nError message: $errorMessage"
                Log.e(TAG, "Response not successful. code: ${response.code()}")
                return@launchWhenCreated
            }

            ///////
            val todayResult = safeApiCall(Dispatchers.IO) {
                RetrofitInstance.currentWeatherService.getCurrentWeather(location.latitude, location.longitude)
            }
            val todayResponse = todayResult.getOrElse {
                exceptionHandle(it)
                return@launchWhenCreated
            }
            if (todayResponse.isSuccessful) {
                setCurrentWeather(todayResponse.body())
            }
        }
    }

    fun setHistoricalWeather(weather: HistoricalWeatherData?) {
        binding.tvYesterday.text =
            if (weather != null) "어제의 기온(${weather.dataPointWeather.dt.toDate().getDateFormat()}): ${weather.dataPointWeather.temp}도"
            else "body is null"
    }

    fun setCurrentWeather(weather: CurrentWeatherData?) {
        binding.tvToday.text =
            if (weather != null) "오늘의 기온(${weather.dt.toDate().getDateFormat()}): ${weather.main.temp}도"
            else "body is null"
    }

    fun exceptionHandle(e: Throwable) {
        when (e) {
            is IOException -> Log.e(TAG, "IOException, you might not have internet connection")
            is HttpException -> Log.e(TAG, "HttpException, unexpected response")
            else -> Log.e(TAG, "${e.message}")
        }
    }

    suspend fun getCurrentLocation(): Location {
        if (checkPermissions(this)) {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers = locationManager.getProviders(true)

            // locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)가 null일 때를 방지하기 위함
            val bestLocation = providers.map {
                locationManager.getLastKnownLocation(it)
            }.filterNotNull().minByOrNull { it.accuracy }!! // accuracy가 가장 작은 Location 선택
            return bestLocation
        }
        TODO("권한 거부 됐을 때")
    }
}
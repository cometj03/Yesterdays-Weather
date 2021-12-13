package com.ctrlz.yesterdays_weather.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ctrlz.yesterdays_weather.data.CurrentWeatherData
import com.ctrlz.yesterdays_weather.data.HistoricalWeatherData
import com.ctrlz.yesterdays_weather.databinding.ActivityMainBinding
import com.ctrlz.yesterdays_weather.network.RetrofitInstance
import com.ctrlz.yesterdays_weather.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

   private lateinit var binding: ActivityMainBinding
   private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        lifecycleScope.launchWhenCreated {

            val location = getCurrentLocation()

            launch {
                val result = safeApiCall(Dispatchers.IO) {
                    val yesterdayTime = Date().moveDay(-1).getSecondTimestamp()
                    RetrofitInstance.historicalWeatherService.getBeforeWeather(location.latitude, location.longitude, yesterdayTime)
                }
                val response = result.getOrElse {
                    exceptionHandle(it)
                    return@launch
                }
                if (response.isSuccessful) {
                    setHistoricalWeather(response.body())
                } else {
                    val jsonObject = JSONObject(response.errorBody()?.toString() ?: "") // errorBody?.string()
                    val errorMessage = jsonObject.getString("message")

                    binding.tvYesterday.text = "Response not successful. code: ${response.code()}\n\nError message: $errorMessage"
                    return@launch
                }
            }

            launch {
                val todayResult = safeApiCall(Dispatchers.IO) {
                    RetrofitInstance.currentWeatherService.getCurrentWeather(
                        location.latitude,
                        location.longitude
                    )
                }
                val todayResponse = todayResult.getOrElse {
                    exceptionHandle(it)
                    return@launch
                }
                if (todayResponse.isSuccessful) {
                    setCurrentWeather(todayResponse.body())
                }
            }
        }

        binding.btTest.setOnClickListener {

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
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        val isAllPermissionsGranted = permissions.all { ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }

        if (isAllPermissionsGranted || requestPermissions(*permissions)) {

            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers = locationManager.getProviders(true)

            // val bestLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) -> sometimes returns null
            val bestLocation = providers.mapNotNull {
                locationManager.getLastKnownLocation(it)
            }.minByOrNull {
                it.accuracy // accuracy가 가장 작은 Location 선택
            }
            return bestLocation!!
        }

        TODO("권한 거부됨")
    }
}
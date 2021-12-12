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
import androidx.lifecycle.lifecycleScope
import com.ctrlz.yesterdays_weather.data.CurrentWeatherData
import com.ctrlz.yesterdays_weather.data.HistoricalWeatherData
import com.ctrlz.yesterdays_weather.databinding.ActivityMainBinding
import com.ctrlz.yesterdays_weather.network.RetrofitInstance
import com.ctrlz.yesterdays_weather.network.safeApiCall
import com.ctrlz.yesterdays_weather.util.DateExtensions.getDateFormat
import com.ctrlz.yesterdays_weather.util.DateExtensions.getSecondTimestamp
import com.ctrlz.yesterdays_weather.util.DateExtensions.moveDay
import com.ctrlz.yesterdays_weather.util.DateExtensions.toDate
import com.gun0912.tedpermission.coroutine.TedPermission
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
                val todayTime = Date().getSecondTimestamp()
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
        if (checkPermission()) {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!! // TODO: NullPointerExecption
        } else {
            TODO("권한 거부 됐을 때")
        }
    }

    suspend fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                return true
        val permissionResult = getPermissionResult()
        return permissionResult.isGranted
    }

    suspend fun getPermissionResult() = TedPermission.create()
            .setRationaleTitle("위치 권한")
            .setRationaleMessage("현재 위치의 날씨를 불러오려면 위치 권한을 허용해주세요.")
            .setDeniedTitle("권한 거부됨")
            .setDeniedMessage("권한을 거부하면 서비스를 정상적으로 이용할 수 없습니다.\n\n[설정] > [권한]에서 설정할 수 있습니다.")
            .setGotoSettingButtonText("설정")
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .check()
}
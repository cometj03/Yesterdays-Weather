package com.ctrlz.yesterdays_weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.ctrlz.yesterdays_weather.data.HistoricalWeatherData
import com.ctrlz.yesterdays_weather.databinding.ActivityMainBinding
import com.ctrlz.yesterdays_weather.network.RetrofitInstance
import com.ctrlz.yesterdays_weather.network.safeApiCall
import com.ctrlz.yesterdays_weather.util.getDateFormat
import com.ctrlz.yesterdays_weather.util.getSecondTimestamp
import com.ctrlz.yesterdays_weather.util.moveDay
import com.ctrlz.yesterdays_weather.util.toDate
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    // TODO: 위도와 경도 구하기기

   private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            val result = safeApiCall(Dispatchers.IO) {
                // RetrofitInstance.currentWeatherService.getCurrentWeather("Seoul")

                val time = Date().moveDay(-1).getSecondTimestamp()

                RetrofitInstance.historicalWeatherService.getBeforeWeather(37.61, 126.91, time)
            }

            val response = result.getOrElse {
                when (it) {
                    is IOException -> Log.e(TAG, "IOException, you might not have internet connection")
                    is HttpException -> Log.e(TAG, "HttpException, unexpected response")
                    else -> Log.e(TAG, "${it.message}")
                }
                return@launchWhenCreated
            }

            if (response.isSuccessful) {
                setHistoricalWeather(response.body())
            } else {
                val jsonObject = JSONObject(response.errorBody()?.string() ?: "")
                val errorMessage = jsonObject.getString("message")

                binding.tvTest.text = "Response not successful. code: ${response.code()}\n\nError message: $errorMessage"
                Log.e(TAG, "Response not successful. code: ${response.code()}")
            }
        }
    }

    fun setHistoricalWeather(weather: HistoricalWeatherData?) {
        if (weather == null) {
            binding.tvTest.text = "body is null"
            return
        }
        binding.tvTest.text = """
            날짜: ${weather.dataPointWeather.dt.toDate().getDateFormat()}
            기온: ${weather.dataPointWeather.temp}도
        """.trimIndent()
    }
}
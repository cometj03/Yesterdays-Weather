package com.ctrlz.yesterdays_weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.ctrlz.yesterdays_weather.databinding.ActivityMainBinding
import com.ctrlz.yesterdays_weather.network.RetrofitInstance
import com.ctrlz.yesterdays_weather.network.safeApiCall
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException

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
                RetrofitInstance.currentWeatherService.getCurrentWeather("Seoul")
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
                response.body()?.let {
                    binding.tvTest.text = it.toString()
                } ?: Log.e(TAG, "Body is null")
            } else {
                Log.e(TAG, "Response not successful. code: ${response.code()}")
            }
        }
    }
}
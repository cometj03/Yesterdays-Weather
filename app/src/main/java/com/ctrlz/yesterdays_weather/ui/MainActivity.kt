package com.ctrlz.yesterdays_weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.ctrlz.yesterdays_weather.databinding.ActivityMainBinding
import com.ctrlz.yesterdays_weather.network.RetrofitInstance
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

        Log.e(TAG, "onCreate: Current Time: ${System.currentTimeMillis()}")

        lifecycleScope.launchWhenCreated {
            val response = try {
                //RetrofitInstance.currentWeatherService.getCurrentWeather("Seoul")
                val currentSecond = System.currentTimeMillis() / 1000
                Log.e(TAG, "onCreate: ${currentSecond.toInt()}", )
                RetrofitInstance.historicalWeatherService.getBeforeWeather(10.0, 10.0, currentSecond.toInt()-1)
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            } catch (e: Throwable) {
                Log.e(TAG, "${e.stackTrace}")
                Log.e(TAG, "${e.message}")
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
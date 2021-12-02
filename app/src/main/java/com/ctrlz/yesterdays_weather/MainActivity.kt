package com.ctrlz.yesterdays_weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ctrlz.yesterdays_weather.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    // TODO: 위도와 경도 구하기기

   private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getCurrentWeather("Seoul")
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "IOException, you might not have internet connection", Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            } catch (e: HttpException) {
                Toast.makeText(this@MainActivity, "HttpException, unexpected response", Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                binding.tvTest.text = response.body().toString()
            } else {
                Toast.makeText(this@MainActivity, "Response not successful", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
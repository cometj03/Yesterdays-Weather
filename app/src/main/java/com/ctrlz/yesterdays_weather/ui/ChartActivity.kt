package com.ctrlz.yesterdays_weather.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.ctrlz.yesterdays_weather.databinding.ActivityChartBinding

class ChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChartBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
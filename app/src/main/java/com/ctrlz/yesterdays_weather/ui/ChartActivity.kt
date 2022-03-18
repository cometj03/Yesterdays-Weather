package com.ctrlz.yesterdays_weather.ui

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ctrlz.yesterdays_weather.R
import com.ctrlz.yesterdays_weather.databinding.ActivityChartBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class ChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChartBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val xvalue = ArrayList<String>().apply {
//            add("11:00 AM")
//            add("12:00 AM")
//            add("1:00 PM")
//            add("2:00 PM")
//        }
//
//        val lineEntry = ArrayList<Entry>().apply {
//            add(Entry(1f, 20f))
//            add(Entry(2f, 50f))
//            add(Entry(3f, 60f))
//            add(Entry(4f, 30f))
//        }
//
//        val lineDataSet = LineDataSet(lineEntry, "DataSet 1").apply {
//            color = Color.BLACK
//            setCircleColor(ContextCompat.getColor(this@ChartActivity, R.color.black))
//        }
//        val data = LineData(lineDataSet)
//
//        binding.lineChart.data = data
//        binding.lineChart.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
//        binding.lineChart.animateXY(3000, 3000)
    }
}
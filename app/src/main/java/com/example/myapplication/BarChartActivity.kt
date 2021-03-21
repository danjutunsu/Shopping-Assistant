package com.example.myapplication

import android.graphics.Color.RED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class BarChartActivity : AppCompatActivity() {

    var results = mutableListOf<BarEntry>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        supportActionBar?.title = "Bar Chart"

        var barChart:BarChart = findViewById(R.id.barChart)

        results.add(BarEntry(20f, positiveResults[0].toFloat() * 100) as BarEntry)
        results.add(BarEntry(25f, negativeResults[0].toFloat() * 100) as BarEntry)

        var barDataSet = BarDataSet(results, "Results %")
        var colorfulColors = ColorTemplate.COLORFUL_COLORS.toMutableList()
        barDataSet.colors = colorfulColors
        barDataSet.valueTextSize = 16f

        var barData = BarData(barDataSet)

        barChart.setFitBars(true)
        barChart.data = barData
        barChart.description.text = "Bar Chart"
        barChart.animateY(2000)
    }
}
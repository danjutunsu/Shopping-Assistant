package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class RadarChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar_chart)

        supportActionBar?.title = "Radar Chart"

        var radarChart: RadarChart = findViewById(R.id.radarChart)

        var results = mutableListOf<RadarEntry>()
        results.add(RadarEntry(positiveResults[0].toFloat() * 100))
        results.add(RadarEntry(negativeResults[0].toFloat() * 100))

        var radarDataSet = RadarDataSet(results, "Results")
        var colorfulColors = ColorTemplate.COLORFUL_COLORS.toMutableList()
        radarDataSet.colors = colorfulColors
        radarDataSet.lineWidth = 2f
        radarDataSet.valueTextColor = Color.RED
        radarDataSet.valueTextSize = 14f

        var radarData = RadarData()
        radarData.addDataSet(radarDataSet)

        var labels = {}

        var xAxis: XAxis = radarChart.xAxis

        xAxis.valueFormatter = IndexAxisValueFormatter()

        radarChart.data = radarData
    }
}
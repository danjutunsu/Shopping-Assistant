package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class PieChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)

        supportActionBar?.title = "Pie Chart"

        var pieChart: PieChart = findViewById(R.id.pieChart)

        var results = mutableListOf<PieEntry>()

        results.add(PieEntry(positiveResults[0].toFloat() * 100, "Positive"))
        results.add(PieEntry(negativeResults[0].toFloat() * 100, "Negative"))

        var pieDataSet = PieDataSet(results, "Results %")
        val listColors = ArrayList<Int>()
        listColors.add(Color.RED)
        listColors.add(Color.BLUE)
        var colorfulColors = ColorTemplate.COLORFUL_COLORS.toMutableList()
        pieDataSet.colors = colorfulColors
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        var pieData = PieData(pieDataSet)

        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.centerText = "Results"
        pieChart.animate()
    }
}
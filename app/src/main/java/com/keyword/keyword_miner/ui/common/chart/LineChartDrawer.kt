package com.keyword.keyword_miner.ui.common.chart

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.keyword.keyword_miner.R


class LineChartDrawer : ChartHelper<LineChart> {
    lateinit var valueList : List<Double>
    lateinit var dateList: List<String>
    override fun setChart(
        chart: LineChart,
        context: Context,
        cnt: List<Double>,
        period: List<String>
    ) {
        valueList = cnt
        dateList =period
        initBarChart(chart)
        chart.setScaleEnabled(false) //Zoom In/Out
        val entries: ArrayList<Entry> = ArrayList()
        val title = "분석 결과"

        for (i in 0 until valueList.size) {
            val lineEntry = Entry(i.toFloat(), valueList[i].toFloat())
            entries.add(lineEntry)
        }

        val lineDataSet = LineDataSet(entries, title)
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable= ContextCompat.getDrawable(context, R.drawable.linechart)
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(ContextCompat.getColor(context, R.color.white)); //LineChart에서 Line Color 설정
        lineDataSet.setCircleColor(ContextCompat.getColor(context, R.color.purple_line)); // LineChart에서 Line Circle Color 설정
        lineDataSet.setCircleHoleColor(ContextCompat.getColor(context, R.color.white))
        val data = LineData(lineDataSet)
        chart.data = data
        chart.invalidate()

    }
    private fun initBarChart(lineChart: LineChart) {


        //hiding the grey background of the chart, default false if not set
        lineChart.setDrawGridBackground(false )
        //remove the bar shadow, default false if not set

        //remove border of the chart, default false if not set
        lineChart.setDrawBorders(false)

        //remove the description label text located at the lower right corner
        val description = Description()
        description.setEnabled(false)
        lineChart.setDescription(description)

        //X, Y 바의 애니메이션 효과
        lineChart.animateY(1000)
        lineChart.animateX(1000)

        //바텀 좌표 값
        val xAxis: XAxis = lineChart.getXAxis()
        //change the position of x-axis to the bottom
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //set the horizontal distance of the grid line
        xAxis.granularity = 1f
        xAxis.textColor = Color.BLACK
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false)
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(true)

        xAxis.valueFormatter = IndexAxisValueFormatter(dateList)
        //좌측 값 hiding the left y-axis line, default true if not set
        val leftAxis: YAxis = lineChart.getAxisLeft()
        leftAxis.setDrawAxisLine(false)
        leftAxis.textColor = Color.BLACK
        xAxis.setLabelCount(4, true);

        //바차트의 타이틀
        val legend: Legend = lineChart.getLegend()
        legend.textSize = 11f
        legend.textColor = Color.BLACK
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

    }
}
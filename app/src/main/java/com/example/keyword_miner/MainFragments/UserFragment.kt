package com.example.keyword_miner.MainFragments

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.keyword_miner.KeywordSearch.KeywordActivity
import com.example.keyword_miner.KeywordSearch.KeywordViewModel
import com.example.keyword_miner.Model.ItemPeriod
import com.example.keyword_miner.Model.MyBlogData
import com.example.keyword_miner.R
import com.example.keyword_miner.User.UserBlogViewmodel
import com.example.keyword_miner.databinding.FragmentKeywordBinding
import com.example.keyword_miner.databinding.FragmentUserBinding
import com.example.keyword_miner.utils.constant
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class UserFragment : Fragment() {
    var CntList = listOf<MyBlogData>()
    lateinit var binding : FragmentUserBinding
    val userBlgoViewModel by activityViewModels<UserBlogViewmodel>()
    val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
    val db = FirebaseFirestore.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater)
        var keyword=""
        val collectionRef = db.collection("keywordDB")
        userBlgoViewModel.currentBlogData.observe(viewLifecycleOwner, Observer { userdata->
            binding.name.text=userdata.name
        })

        userBlgoViewModel.currentUserBLogCnt.observe(viewLifecycleOwner, Observer { blogcnt->
            CntList=blogcnt
            setChartView(binding)
        })
        binding.recommendBtn.setOnClickListener {
            collectionRef.whereEqualTo("date", today)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        keyword = document.getString("keyword").toString()
                        Log.d("HHH", "Today's keyword: $keyword")
                    }
                    val intent = Intent(requireActivity(), KeywordActivity::class.java)
                    intent.putExtra("searchterm", keyword)

                    startActivity(intent)
                }
                .addOnFailureListener { exception ->
                    Log.w("HHH", "Error getting documents: ", exception)
                }
        }

        return binding.root
    }
    private fun setChartView(view: FragmentUserBinding?) {
        var chartWeek = binding.blogChartWeek
        setWeek(chartWeek)

    }

    private fun setWeek(barChart: BarChart) {
        initBarChart(barChart)

        barChart.setScaleEnabled(false) //Zoom In/Out

        val valueList : List<Double> = CntList[0].cnt
        //val entries: ArrayList<String> = PeriodList[0].period
        val entries: ArrayList<BarEntry> = ArrayList()
        val title = CntList[0].title

        //input data

        for (i in 0 until valueList.size) {
            val barEntry = BarEntry(i.toFloat(), valueList[i].toFloat())
            entries.add(barEntry)
        }


        val barDataSet = BarDataSet(entries, title)
        val data = BarData(barDataSet)
        barChart.data = data
        barChart.invalidate()
    }

    private fun initBarChart(barChart: BarChart) {
        val dateList: List<String> = CntList[0].period
        //hiding the grey background of the chart, default false if not set
        barChart.setDrawGridBackground(false)
        //remove the bar shadow, default false if not set
        barChart.setDrawBarShadow(false)
        //remove border of the chart, default false if not set
        barChart.setDrawBorders(false)

        //remove the description label text located at the lower right corner
        val description = Description()
        description.setEnabled(false)
        barChart.setDescription(description)

        //X, Y 바의 애니메이션 효과
        barChart.animateY(1000)
        barChart.animateX(1000)


        //바텀 좌표 값
        val xAxis: XAxis = barChart.getXAxis()
        //change the position of x-axis to the bottom
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //set the horizontal distance of the grid line
        xAxis.granularity = 1f
        xAxis.textColor = Color.RED
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false)
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false)

        xAxis.valueFormatter = IndexAxisValueFormatter(dateList)
        //좌측 값 hiding the left y-axis line, default true if not set
        val leftAxis: YAxis = barChart.getAxisLeft()
        leftAxis.setDrawAxisLine(false)
        leftAxis.textColor = Color.RED


        //우측 값 hiding the right y-axis line, default true if not set
        val rightAxis: YAxis = barChart.getAxisRight()
        rightAxis.setDrawAxisLine(false)
        rightAxis.textColor = Color.RED


        //바차트의 타이틀
        val legend: Legend = barChart.getLegend()
        //setting the shape of the legend form to line, default square shape
        legend.form = Legend.LegendForm.LINE
        //setting the text size of the legend
        legend.textSize = 11f
        legend.textColor = Color.BLACK
        //setting the alignment of legend toward the chart
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        //setting the stacking direction of legend
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false)
    }



}
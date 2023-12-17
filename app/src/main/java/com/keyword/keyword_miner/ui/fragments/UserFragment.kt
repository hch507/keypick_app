package com.keyword.keyword_miner.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.keyword.keyword_miner.ui.activity.KeywordActivity
import com.keyword.keyword_miner.R
import com.keyword.keyword_miner.databinding.FragmentUserBinding
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
import com.keyword.keyword_miner.ui.viewmodels.UserBlogViewModel
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class UserFragment : Fragment() {
    lateinit var userEmail: String
    lateinit var keyword : String
    lateinit var binding : FragmentUserBinding
    val userBlogViewModel: UserBlogViewModel by viewModels()
    lateinit var visitCnt : List<Int>
    lateinit var visitPeriod : List<String>




    val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
    val db = FirebaseFirestore.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater)
        val collectionRef = db.collection("keywordDB")

        userEmail= userBlogViewModel.getUserEmail()
        userBlogViewModel.getUserBlogData(userEmail)
        binding.name.text=userEmail

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                userBlogViewModel.currentBlogCnt.collectLatest {
                    when(it){
                        is MainUiState.success ->{
                            visitCnt =it.data.visitorcntList.map { it.cnt.toInt() }
                            visitPeriod= it.data.visitorcntList.map { it.id }
                            Log.d("hhh", "onCreateView: ${visitCnt}")
                            var gapCnt = calculate(visitCnt)
                            binding.apply {
                                todayText.text = "${visitCnt[4]}명"
                                gap.apply {
                                    if (gapCnt<0){
                                        text ="${gapCnt} 명"
                                        setTextColor(Color.BLUE)
                                    }else if (gapCnt>0){
                                        setTextColor(Color.RED)
                                        text ="+${gapCnt} 명"
                                    }else{
                                        setTextColor(Color.BLACK)
                                    }
                                }
                            }
                            setChartView(binding)
                        }
                        is MainUiState.Error -> {

                        }
                        is MainUiState.Loading -> {

                        }
                    }
                }
            }
        }
        binding.recommendBtn.setOnClickListener {
            collectionRef.whereEqualTo("date", today)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.size() > 0) {
                            for (document in documents) {
                            keyword = document.getString("keyword").toString()
                            Log.d("HHH", "Today's keyword: $keyword")
                        }
                        val intent = Intent(requireActivity(), KeywordActivity::class.java)
                        intent.putExtra("searchterm", keyword)

                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(requireContext(), "오늘은 추천코드룰 찾지 못했습니다. 죄송합니다ㅠ.", Toast.LENGTH_SHORT).show()
                    }

                }
                .addOnFailureListener { exception ->
                    Log.w("HHH", "Error getting documents: ", exception)
                }
        }

        return binding.root
    }
    private fun calculate(Cnt : List<Int>) : Int{
        val gap = Cnt[4] - Cnt[3]
        return gap
    }
    private fun setChartView(view: FragmentUserBinding?) {
        var chartWeek = binding.blogChartWeek
        setWeek(chartWeek)

    }

    private fun setWeek(barChart: BarChart) {
        initBarChart(barChart)

        barChart.setScaleEnabled(false) //Zoom In/Out

        val valueList : List<Int> = visitCnt
        //val entries: ArrayList<String> = PeriodList[0].period
        val entries: ArrayList<BarEntry> = ArrayList()
        val title = "ddoaak"

        //input data

        for (i in 0 until valueList.size) {
            val barEntry = BarEntry(i.toFloat(), valueList[i].toFloat())
            entries.add(barEntry)
        }


        val barDataSet = BarDataSet(entries, title)
        val data = BarData(barDataSet)
        barDataSet.setColor(ContextCompat.getColor(requireContext(),R.color.teal_200))


        barChart.data = data
        barChart.invalidate()
    }

    private fun initBarChart(barChart: BarChart) {
        val dateList: List<String> = visitPeriod
        //hiding the grey background of the chart, default false if not set
        barChart.setDrawGridBackground(false)
        barChart.setBackgroundColor(Color.TRANSPARENT)
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
        xAxis.textColor = Color.BLACK
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false)
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false)

        xAxis.valueFormatter = IndexAxisValueFormatter(dateList)
        //좌측 값 hiding the left y-axis line, default true if not set
        val leftAxis: YAxis = barChart.getAxisLeft()
        leftAxis.setDrawAxisLine(false)
        leftAxis.textColor = Color.BLACK


        //우측 값 hiding the right y-axis line, default true if not set
        val rightAxis: YAxis = barChart.getAxisRight()
        rightAxis.setDrawAxisLine(false)
        rightAxis.textColor = Color.BLACK


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
package com.example.keyword_miner.KeywordSearch

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.keyword_miner.KeywordInfo
import com.example.keyword_miner.Model.ItemPeriod
import com.example.keyword_miner.Model.blogData
import com.example.keyword_miner.R
import com.example.keyword_miner.Repository.RepositoryItem
import com.example.keyword_miner.Room.Roomhelper
import com.example.keyword_miner.databinding.FragmentKeywordBinding
import com.example.keyword_miner.utils.constant.TAG
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class KeywordFragment : Fragment() {

    var PeriodList = ArrayList<ItemPeriod>()

    var keyword: String=""
    var monthPc: String=""
    var monthMo: String=""
    var monthCnt: String=""
    var total : String=""
    lateinit var binding: FragmentKeywordBinding
    lateinit var helper: Roomhelper

    val keywordViewModel by activityViewModels<KeywordViewModel>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeywordBinding.inflate(layoutInflater)
        helper= Roomhelper.getInstance(requireContext())!!
        //keywordViewModel = ViewModelProvider(requireActivity()).get(KeywordViewModel::class.java)
        Log.d("HCH", "KeywordFragment - onCreateView() - called")
        keywordViewModel.currentRelData.observe(viewLifecycleOwner, Observer{KeywordInfoList->
            Log.d(TAG, "KeywordFragment - onCreateView() - called${KeywordInfoList}")
            binding.apply {
            binding.keyword.text = KeywordInfoList.get(0).relKeyword
            binding.pcClick.text = KeywordInfoList.get(0).monthlyPcQcCnt
            binding.moClick.text = KeywordInfoList.get(0).monthlyMobileQcCnt}

            this.keyword = KeywordInfoList.get(0).relKeyword
            this.monthPc = KeywordInfoList.get(0).monthlyPcQcCnt
            this.monthMo = KeywordInfoList.get(0).monthlyMobileQcCnt
            if(monthPc=="<10"){
                this.monthCnt = monthMo
            }else if(monthMo=="<10"){
                this.monthCnt = monthPc
            }else if(monthPc=="<10"&&monthMo=="<10"){
                this.monthCnt="<10"
            }else{
                this.monthCnt= (monthPc.toInt()+monthMo.toInt()).toString()
            }
        })

        keywordViewModel.currentBlogDate.observe(viewLifecycleOwner, Observer{
            PeriodList=it
            setChartView(binding)
        })

        keywordViewModel.currentMonthCnt.observe(viewLifecycleOwner, Observer{
            var monthCnt = MonthCnt(it.get(0).data)
            binding.monthBlog.text=monthCnt
            binding.totalBlog.text=it.get(0).total

            this.total=it.get(0).total
        })
        binding.storeBtn.setOnClickListener {
            val date = System.currentTimeMillis()
            Log.d("HHH", "KeywordFragment-onCreateView() called${date}")
            val StoreList = RepositoryItem(keyword,monthCnt,total,date)
            insert(StoreList)
            Toast.makeText(getActivity(), "저장되었습니다", Toast.LENGTH_SHORT).show();
        }
        return binding.root
    }

    private fun setChartView(view: FragmentKeywordBinding?) {
        var chartWeek = binding.chartWeek
        setWeek(chartWeek)

    }

    private fun setWeek(barChart: BarChart) {
        initBarChart(barChart)

        barChart.setScaleEnabled(false) //Zoom In/Out

        val valueList : ArrayList<Double> = PeriodList[0].rate
        //val entries: ArrayList<String> = PeriodList[0].period
        val entries: ArrayList<BarEntry> = ArrayList()
        val title = PeriodList[0].title

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
        val dateList: ArrayList<String> = PeriodList[0].period
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun MonthCnt(list: ArrayList<String>):String{
        val list: List<String> = list.toList()
        val thirtyDaysAgo = LocalDate.now().minusDays(30)
        val count = list.filter { LocalDate.parse("$it", DateTimeFormatter.ofPattern("yyyyMMdd")) >= thirtyDaysAgo }.count()
        Log.d(TAG, "KeywordActivity - MonthCnt() - called $count")
        return if (count == 100) {
            "+$count"
        } else {
            count.toString()
        }

    }
    fun insert(item : RepositoryItem){
        Log.d("HCH", "KeywordFragment-insert() called")
        CoroutineScope(Dispatchers.IO).launch {
            helper.roomDao().insert(item)
        }
    }
}
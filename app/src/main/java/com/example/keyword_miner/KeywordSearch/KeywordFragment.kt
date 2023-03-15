package com.example.keyword_miner.KeywordSearch

import android.content.Context
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.keyword_miner.Model.ItemPeriod
import com.example.keyword_miner.R
import com.example.keyword_miner.Repository.RepositoryItem
import com.example.keyword_miner.Room.Roomhelper
import com.example.keyword_miner.databinding.FragmentKeywordBinding
import com.example.keyword_miner.utils.constant.TAG
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


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
            val date = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())
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
        chartWeek.setOnChartValueSelectedListener(object: OnChartValueSelectedListener{
            override fun onValueSelected(e: Entry, h: Highlight){
                val xAxisLabel = e.x.let{
                    chartWeek.xAxis.valueFormatter.getAxisLabel(it, chartWeek.xAxis)
                }
                binding.date.text= xAxisLabel
            }
            override fun onNothingSelected() {
            }
        })
    }

    private fun setWeek(lineChart: LineChart) {
        initBarChart(lineChart)

        lineChart.setScaleEnabled(false) //Zoom In/Out

        val valueList : ArrayList<Double> = PeriodList[0].rate
        //val entries: ArrayList<String> = PeriodList[0].period
        val entries: ArrayList<Entry> = ArrayList()
        val title = PeriodList[0].title

        //input data

        for (i in 0 until valueList.size) {
            val lineEntry = Entry(i.toFloat(), valueList[i].toFloat())
            entries.add(lineEntry)
        }


        val lineDataSet = LineDataSet(entries, title)
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable= ContextCompat.getDrawable(context!!, R.drawable.linechart)
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(ContextCompat.getColor(context!!, R.color.white)); //LineChart에서 Line Color 설정
        lineDataSet.setCircleColor(ContextCompat.getColor(context!!, R.color.purple_line)); // LineChart에서 Line Circle Color 설정
        lineDataSet.setCircleHoleColor(ContextCompat.getColor(context!!, R.color.white))
        val data = LineData(lineDataSet)
        lineChart.data = data
//        lineChart.data.isHighlightEnabled = false
        lineChart.invalidate()

    }

    private fun initBarChart(lineChart: LineChart) {
        val dateList: ArrayList<String> = PeriodList[0].period
        //hiding the grey background of the chart, default false if not set
        lineChart.setDrawGridBackground(false)
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

        //바차트의 타이틀
        val legend: Legend = lineChart.getLegend()
        legend.textSize = 11f
        legend.textColor = Color.BLACK
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

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
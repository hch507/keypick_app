package com.keyword.keyword_miner.ui.fragments


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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.keyword.keyword_miner.domain.Model.ItemPeriod
import com.keyword.keyword_miner.R
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.data.Room.Roomhelper
import com.keyword.keyword_miner.databinding.FragmentKeywordBinding
import com.keyword.keyword_miner.utils.constant.TAG
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.keyword.keyword_miner.domain.Model.monthRadioData.MonthRatioDataModel
import com.keyword.keyword_miner.ui.viewmodels.KeywordViewModel
import com.keyword.keyword_miner.ui.viewmodels.keywordViewmodelTest
import com.keyword.keyword_miner.utils.MainUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class KeywordFragment : Fragment() {

    var periodList = ArrayList<ItemPeriod>()
    lateinit var periodRadioData : List<MonthRatioDataModel>
    var keyword: String=""
    var monthPc: String=""
    var monthMo: String=""
    var monthCnt: String=""
    var total : String=""
    lateinit var binding: FragmentKeywordBinding
    lateinit var helper: Roomhelper

    val keywordViewModel by activityViewModels<KeywordViewModel>()
    val keywordViewmodelTest by activityViewModels<keywordViewmodelTest>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeywordBinding.inflate(layoutInflater)
        helper= Roomhelper.getInstance(requireContext())!!

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                keywordViewmodelTest.currentBlogTotal.collectLatest {
                    when(it){
                        is MainUiState.success ->{
                            var monthCnt = MonthCnt(it.data.blogData.map { it.date })
                            Log.d("hchh", "KeywordFragment - onCreateView() - called${it.data.blogData.map { it.date }}")
                            binding.apply {
                                monthBlog.text=monthCnt
                                totalBlog.text=it.data.total.toString()
                            }

                        }
                        is MainUiState.Error ->{
                            Toast.makeText(getActivity(), "서버와 통신이 실패하였습니다", Toast.LENGTH_SHORT).show()
                        }
                        is MainUiState.Loading ->{

                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                keywordViewmodelTest.currentMonthRatio.collectLatest {
                    when(it){
                        is MainUiState.success ->{
                            periodRadioData= it.data
                            setChartView(binding)

                        }
                        is MainUiState.Error ->{
                            Toast.makeText(getActivity(), "서버와 통신이 실패하였습니다", Toast.LENGTH_SHORT).show()
                        }
                        is MainUiState.Loading ->{

                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                keywordViewmodelTest.currentRelData.collectLatest {
                    when(it){
                        is MainUiState.success ->{
                            binding.apply {
                                Log.d("hhh", "KeywordFragment - onCreateView() - called ${it.data}")
                                keyword.text = it.data.get(0).relKeyword
                                pcClick.text = it.data.get(0).monthlyPcQcCnt
                                moClick.text = it.data.get(0).monthlyMobileQcCnt
                            }

                        }
                        is MainUiState.Error ->{
                            Toast.makeText(getActivity(), "서버와 통신이 실패하였습니다", Toast.LENGTH_SHORT).show()
                        }
                        is MainUiState.Loading ->{

                        }
                    }
                }
            }
        }

        binding.storeBtn.setOnClickListener {
            val date = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())
            val StoreList = KeywordSaveModel(keyword,monthCnt,total,date)
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

//        val valueList : ArrayList<Double> = periodList[0].rate
        val valueList : List<Double> =
            periodRadioData.get(0).ratioData?.map { it.rate?.toDouble() ?: return } ?: return
        //val entries: ArrayList<String> = PeriodList[0].period
        val entries: ArrayList<Entry> = ArrayList()
        val title = periodRadioData.get(0).title

        //input data

        for (i in 0 until valueList.size) {
            val lineEntry = Entry(i.toFloat(), valueList[i].toFloat())
            entries.add(lineEntry)
        }


        val lineDataSet = LineDataSet(entries, title)
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable= ContextCompat.getDrawable(requireContext(), R.drawable.linechart)
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(ContextCompat.getColor(requireContext(), R.color.white)); //LineChart에서 Line Color 설정
        lineDataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.purple_line)); // LineChart에서 Line Circle Color 설정
        lineDataSet.setCircleHoleColor(ContextCompat.getColor(requireContext(), R.color.white))
        val data = LineData(lineDataSet)
        lineChart.data = data
//        lineChart.data.isHighlightEnabled = false
        lineChart.invalidate()

    }

    private fun initBarChart(lineChart: LineChart) {
//        val dateList: ArrayList<String> = periodList[0].period
        val dateList: List<String> =
            periodRadioData.get(0).ratioData?.map { it.period.toString()} ?:return
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun MonthCnt(list: List<String>):String{
        val thirtyDaysAgo = LocalDate.now().minusDays(30)
        val count = list.filter { LocalDate.parse("$it", DateTimeFormatter.ofPattern("yyyyMMdd")) >= thirtyDaysAgo }.count()
        Log.d(TAG, "KeywordActivity - MonthCnt() - called $count")
        return if (count == 100) {
            "+$count"
        } else {
            count.toString()
        }

    }
    fun insert(item : KeywordSaveModel){
        Log.d("HCH", "KeywordFragment-insert() called")
        CoroutineScope(Dispatchers.IO).launch {
            helper.roomDao().insert(item)
        }
    }
}
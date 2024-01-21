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
import com.keyword.keyword_miner.R
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.data.local.Room.Roomhelper
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
import com.keyword.keyword_miner.domain.model.monthRadioData.MonthRatioDataModel
import com.keyword.keyword_miner.ui.common.chart.LineChartDrawer
import com.keyword.keyword_miner.ui.viewmodels.KeywordViewModel
import com.keyword.keyword_miner.utils.MainUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class KeywordFragment : Fragment() {

    lateinit var periodRadioData : List<MonthRatioDataModel>
    lateinit var keywordCnt : List<Double>
    lateinit var keywordPeriod : List<String>
    lateinit var keywordName: String
    lateinit var currentMonthCnt: String
    lateinit var total : String
    lateinit var binding: FragmentKeywordBinding
    lateinit var helper: Roomhelper
    var lineChartDrawer:LineChartDrawer = LineChartDrawer()
    val keywordViewModel by activityViewModels<KeywordViewModel>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeywordBinding.inflate(layoutInflater)
        helper= Roomhelper.getInstance(requireContext())!!

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    keywordViewModel.currentBlogTotal.collectLatest {
                        when (it) {
                            is MainUiState.success -> {
                                var monthCnt = MonthCnt(it.data.blogData.map { it.date })
                                Log.d(
                                    "hchh",
                                    "KeywordFragment - onCreateView() - called${it.data.blogData.map { it.date }}"
                                )
                                binding.apply {
                                    monthBlogTextVIew.text = monthCnt
                                    total = it.data.total.toString()
                                    totalBlogTextView.text = total
                                }

                            }

                            is MainUiState.Error -> {
                                Toast.makeText(getActivity(), "서버와 통신이 실패하였습니다", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is MainUiState.Loading -> {

                            }
                        }
                    }
                }
                launch {
                    keywordViewModel.currentMonthRatio.collectLatest {
                        when(it){
                            is MainUiState.success ->{
                                periodRadioData= it.data
                                keywordCnt= periodRadioData.get(0).ratioData?.map { it.rate?.toDouble() ?: 0.0} ?: emptyList()
                                keywordPeriod= periodRadioData.get(0).ratioData?.map { it.period.toString()} ?: emptyList()
                                lineChartDrawer.setChart(binding.keywordLineChart , requireContext(), keywordCnt, keywordPeriod)
//
                            }
                            is MainUiState.Error ->{
                                Toast.makeText(getActivity(), "서버와 통신이 실패하였습니다", Toast.LENGTH_SHORT).show()
                            }
                            is MainUiState.Loading ->{

                            }
                        }
                    }
                }
                launch {
                    keywordViewModel.currentRelData.collectLatest {
                        when(it){
                            is MainUiState.success ->{
                                binding.apply {
                                    Log.d("hhh", "KeywordFragment - onCreateView() - called ${it.data}")
                                    keywordName = it.data.get(0).relKeyword.toString()
                                    searchTextView.text = keywordName
                                    pcMonthTextView.text = it.data.get(0).monthlyPcQcCnt
                                    moMonthTextView.text = it.data.get(0).monthlyMobileQcCnt
                                    currentMonthCnt = (it.data.get(0).monthlyPcQcCnt!!.toInt() +it.data.get(0).monthlyMobileQcCnt!!.toInt()).toString()
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
        }
        binding.keywordLineChart.setOnChartValueSelectedListener(object: OnChartValueSelectedListener{
            override fun onValueSelected(e: Entry, h: Highlight){
                val xAxisLabel = e.x.let{
                    binding.keywordLineChart.xAxis.valueFormatter.getAxisLabel(it, binding.keywordLineChart.xAxis)
                }
                binding.date.text= xAxisLabel
            }
            override fun onNothingSelected() {
            }
        })


        binding.storeButton.setOnClickListener {
            val date = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())
            val storeList = KeywordSaveModel(keywordName,currentMonthCnt,total,date)
            keywordViewModel.insertKeyword(storeList)
            Log.d("insert", "KeywordFragment-onCreateView() called ${storeList}")
            Toast.makeText(getActivity(), "저장되었습니다", Toast.LENGTH_SHORT).show();
        }
        return binding.root

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

}
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
import com.keyword.keyword_miner.ui.common.chart.BarChartDrawer
import com.keyword.keyword_miner.ui.common.chart.ChartHelper
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
    lateinit var keyword: String
    lateinit var binding: FragmentUserBinding
    val userBlogViewModel: UserBlogViewModel by viewModels()
    lateinit var visitCnt: List<Double>
    lateinit var visitPeriod: List<String>
    var barChart: ChartHelper<BarChart> = BarChartDrawer()


    val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
    val db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater)
        val collectionRef = db.collection("keywordDB")

        userEmail = userBlogViewModel.getUserEmail()
        userBlogViewModel.getUserBlogData(userEmail)
        binding.nameTextView.text = userEmail

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userBlogViewModel.currentBlogCnt.collectLatest {
                    when (it) {
                        is MainUiState.success -> {
                            visitCnt = it.data.visitorcntList.map { it.cnt.toDouble() }
                            visitPeriod = it.data.visitorcntList.map { it.id }
                            Log.d("hhh", "onCreateView: ${visitCnt}")
                            var gapCnt = calculate(visitCnt)
                            binding.apply {
                                todayTextView.text = "${visitCnt[4].toInt()}명"
                                gapTextView.apply {
                                    if (gapCnt < 0) {
                                        text = "${gapCnt} 명"
                                        setTextColor(Color.BLUE)
                                    } else if (gapCnt > 0) {
                                        setTextColor(Color.RED)
                                        text = "+${gapCnt} 명"
                                    } else {
                                        setTextColor(Color.BLACK)
                                        text = "0 명"
                                    }
                                }
                            }
                            barChart.setChart(
                                binding.blogBarChart,
                                requireContext(),
                                visitCnt,
                                visitPeriod
                            )
                        }

                        is MainUiState.Error -> {

                        }

                        is MainUiState.Loading -> {

                        }
                    }
                }
            }
        }
        binding.recommendButton.setOnClickListener {
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
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "오늘은 추천코드룰 찾지 못했습니다. 죄송합니다ㅠ.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("HHH", "Error getting documents: ", exception)
                }
        }

        return binding.root
    }

    private fun calculate(Cnt: List<Double>): Int {
        val gap = Cnt[4] - Cnt[3]
        return gap.toInt()
    }

}
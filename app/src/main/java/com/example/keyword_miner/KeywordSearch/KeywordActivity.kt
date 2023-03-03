package com.example.keyword_miner.KeywordSearch

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.EditText
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.keyword_miner.KeywordInfo
import com.example.keyword_miner.Model.ItemPeriod
import com.example.keyword_miner.Model.blogData
import com.example.keyword_miner.R
import com.example.keyword_miner.databinding.ActivityKeywordBinding
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
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class KeywordActivity : AppCompatActivity() {


    val keywordViewModel: KeywordViewModel by viewModels()

    lateinit var kbinding: ActivityKeywordBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        kbinding = ActivityKeywordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(kbinding.root)
        val top_app_bar = kbinding.topAppBar
        val list = listOf(KeywordFragment(),RelFragment())
        //어답터 생성
        val pageAdapter = FragmentPageAdapter(list,this)
        //어답터와 뷰페이저 연결
        kbinding.ViewPage.adapter=pageAdapter
        //탭 레이아웃 타이틀 설정
        val titles = listOf("검색어","연관검색어")
        //탭 레이아웃과 뷰페이저 연걸
        TabLayoutMediator(kbinding.tabLayout, kbinding.ViewPage){tab, position ->
            tab.text= titles.get(position)
        }.attach()
        val searchTerm = intent.getStringExtra("searchterm")
        //keywordViewModel=ViewModelProvider(this).get(KeywordViewModel::class.java)
        if (searchTerm != null) {
            keywordViewModel.updateKeywordData(searchTerm)
        }
//        setSupportActionBar(top_app_bar)
        kbinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var userSearchInput = query?.replace(" ", "")
                userSearchInput= userSearchInput?.let { convertToUpperCase(it) }
                if (userSearchInput != null) {
                    keywordViewModel.updateKeywordData(userSearchInput)
                }
                kbinding.searchView.setQuery("",false)
                kbinding.searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // 검색창에서 글자가 변경이 일어날 때마다 호출

                return true
            }
        })
    }
    fun convertToUpperCase(input: String): String {
        return if (input.matches("[a-zA-Z]+".toRegex()) && input.contains("[a-z]".toRegex())) {
            input.toUpperCase()
        } else {
            input
        }
    }
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//
//        Log.d(TAG, "KeywordActivity - onCreateOptionsMenu() - called")
//
//        val inflater=menuInflater
//        inflater.inflate(R.menu.top_app_bar_menu,menu)
//
//        val searchManager =getSystemService(Context.SEARCH_SERVICE) as SearchManager
//
//        this.mySearchView = (menu?.findItem(R.id.search)?.actionView as SearchView?)!!
//
//        this.mySearchView.apply {
//            this.queryHint="키워드를 입력해주세요"
//            mySearchViewEditText =this.findViewById(androidx.appcompat.R.id.search_src_text)
//        }
//
//        this.mySearchViewEditText.apply {
//            this.setTextColor(Color.WHITE)
//            this.setHintTextColor(Color.WHITE)
//        }
//
//
//        return true
//    }



}

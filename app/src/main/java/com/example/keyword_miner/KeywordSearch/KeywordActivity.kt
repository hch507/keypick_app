package com.example.keyword_miner.KeywordSearch

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import com.example.keyword_miner.databinding.ActivityKeywordBinding
import com.google.android.material.tabs.TabLayoutMediator
class KeywordActivity : AppCompatActivity() {


    val keywordViewModel: KeywordViewModel by viewModels()

    lateinit var kbinding: ActivityKeywordBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        kbinding = ActivityKeywordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(kbinding.root)

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
        var searchTerm = intent.getStringExtra("searchterm")
        searchTerm= searchTerm?.let { convertToUpperCase(it) }

        if (searchTerm != null) {
            Log.d("HHH", "KeywordActivity - onCreate() - called${searchTerm}")
            keywordViewModel.updateKeywordData(searchTerm)
        }


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

}
package com.keyword.keyword_miner.KeywordSearch

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import com.keyword.keyword_miner.Repository.RepositoryActivity
import com.keyword.keyword_miner.databinding.ActivityKeywordBinding
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
        Log.d("AAAA2", "KeywordActivity - onCreate() - called${searchTerm}")
        searchTerm= searchTerm?.let { convertToUpperCase(it) }
        Log.d("AAAA3", "KeywordActivity - onCreate() - called")
        if (searchTerm != null) {
            Log.d("HHHH1", "KeywordActivity - onCreate() - called${searchTerm}")
            keywordViewModel.updateKeywordData(searchTerm)
        }


        kbinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                var userSearchInput = query?.replace(" ", "")
                Log.d("HHHH2", "KeywordActivity - onCreate() - called${userSearchInput}")
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

                return false
            }
        })
        kbinding.repository.setOnClickListener {
            intent = Intent(this@KeywordActivity, RepositoryActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("GGG", "KeywordActivity - onBackPressed() - called")
        finish()
    }
    fun convertToUpperCase(input: String): String {
        return if (input.matches("[a-zA-Z]+".toRegex()) && input.contains("[a-z]".toRegex())) {
            input.toUpperCase()
        } else {
            input
        }
    }

}

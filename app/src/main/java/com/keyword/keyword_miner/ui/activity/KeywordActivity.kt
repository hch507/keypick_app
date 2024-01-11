package com.keyword.keyword_miner.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import com.keyword.keyword_miner.databinding.ActivityKeywordBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.keyword.keyword_miner.KeywordSearch.FragmentPageAdapter
import com.keyword.keyword_miner.domain.model.relkeyworddata.RelKeywordDataModel
import com.keyword.keyword_miner.ui.fragments.KeywordFragment
import com.keyword.keyword_miner.ui.fragments.RelFragment
import com.keyword.keyword_miner.ui.viewmodels.KeywordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeywordActivity : AppCompatActivity() {

    val keywordViewModel : KeywordViewModel by viewModels()
    lateinit var kbinding: ActivityKeywordBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        kbinding = ActivityKeywordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(kbinding.root)

        val list = listOf(KeywordFragment(), RelFragment())
        val pageAdapter = FragmentPageAdapter(list,this)
        kbinding.ViewPage.adapter=pageAdapter
        val titles = listOf("검색어","연관검색어")
        TabLayoutMediator(kbinding.tabLayout, kbinding.ViewPage){tab, position ->
            tab.text= titles.get(position)
        }.attach()

        var searchTerm = intent.getStringExtra("searchterm")
        searchTerm= searchTerm?.let { convertToUpperCase(it) }

        if (searchTerm != null) {
            Log.d("hch", "KeywordActivity - onCreate() - called")
//            keywordViewModel.updateKeywordData(searchTerm)
            updateKeywordData(searchTerm)

        }


        kbinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                var userSearchInput = query?.replace(" ", "")
                userSearchInput= userSearchInput?.let { convertToUpperCase(it) }
                if (userSearchInput != null) {
//                    keywordViewModel.updateKeywordData(userSearchInput)
                    updateKeywordData(userSearchInput)
                }
                kbinding.searchView.setQuery("",false)
                kbinding.searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
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
    fun updateKeywordData(searchTerm : String){
        keywordViewModel.apply {
            getBlogTotal(searchTerm)
            getRelData(searchTerm)
            getMonthRatioData(searchTerm)
        }
    }
    inner class RelKeywordHandler(){

        fun onClick(relKeyword : RelKeywordDataModel){
            keywordViewModel.apply {
                relKeyword.relKeyword?.let { it1 ->
                    getBlogTotal(it1)
                    getRelData(it1)
                    getMonthRatioData(it1)
                }
            }
        }
    }

}

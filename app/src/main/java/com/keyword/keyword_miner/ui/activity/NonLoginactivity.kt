package com.keyword.keyword_miner.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.keyword.keyword_miner.databinding.ActivityNonLoginactivityBinding
import com.keyword.keyword_miner.ui.viewmodels.keywordViewmodelTest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NonLoginactivity : AppCompatActivity() {
    lateinit var binding : ActivityNonLoginactivityBinding
    private val viewmodel : keywordViewmodelTest by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding =ActivityNonLoginactivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewmodel.getRelData("아이패드")
        viewmodel.getMonthRatioData("아이패드")
        binding.searchViewMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@NonLoginactivity, KeywordActivity::class.java)
                val userSearchInput = query?.replace(" ", "")
                if (userSearchInput != null) {
                    intent.putExtra("searchterm", userSearchInput)
                }
                startActivity(intent)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


        binding.returnBtn.setOnClickListener {
            intent = Intent(this@NonLoginactivity, BlogIdActivity::class.java)
            Log.d("HHH", "NonLoginactivity - onCreate() - called")
            startActivity(intent)
            finish()
        }
    }
}
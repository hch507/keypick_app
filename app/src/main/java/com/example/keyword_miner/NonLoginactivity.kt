package com.example.keyword_miner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import com.example.keyword_miner.KeywordSearch.KeywordActivity
import com.example.keyword_miner.databinding.ActivityNonLoginactivityBinding

class NonLoginactivity : AppCompatActivity() {
    lateinit var binding : ActivityNonLoginactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding =ActivityNonLoginactivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
package com.example.keyword_miner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.keyword_miner.databinding.ActivityKeywordBinding
import com.example.keyword_miner.databinding.ActivityMainBinding
import com.example.keyword_miner.utils.constant.TAG

class KeywordActivity : AppCompatActivity() {
    var keywordList = ArrayList<KeywordInfo>()
    private var kbinding  : ActivityKeywordBinding? = null
    private val binding get() = kbinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        kbinding = ActivityKeywordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d(TAG, "KeywordActivity-onCreate() called")
        val bundle = intent.getBundleExtra("bundle_array")
        

        if (bundle != null) {
            keywordList= bundle.getSerializable("array_list") as ArrayList<KeywordInfo>
            Log.d(TAG, "Recycler_view-onCreate() called${keywordList.get(0)}")

            binding.keyword.text=keywordList.get(0).relKeyword
            binding.pcClick.text=keywordList.get(0).monthlyPcQcCnt
            binding.moClick.text=keywordList.get(0).monthlyMobileQcCnt

        }


    }
}
package com.example.keyword_miner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.keyword_miner.databinding.ActivityKeywordBinding
import com.example.keyword_miner.databinding.ActivityMainBinding

class KeywordActivity : AppCompatActivity() {
    private var kbinding  : ActivityKeywordBinding? = null
    private val binding get() = kbinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        kbinding = ActivityKeywordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}
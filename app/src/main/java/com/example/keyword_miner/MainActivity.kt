package com.example.keyword_miner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.keyword_miner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mbinding  : ActivityMainBinding? = null
    private val binding get() = mbinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}
package com.example.naver_trend_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.naver_trend_api.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mbinding  : ActivityMainBinding? = null
    private val binding get() = mbinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
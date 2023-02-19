package com.example.keyword_miner.Retrofit

import com.example.keyword_miner.utils.API

class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }
    private var iRetrofit : IRetrofit? = RetrofitClient.getRetrifitClient(API.BASE_URL)?.create(IRetrofit::class.java)
}
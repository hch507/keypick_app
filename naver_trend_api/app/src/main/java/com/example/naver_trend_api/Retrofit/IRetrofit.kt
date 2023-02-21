package com.example.keyword_miner.Retrofit


import com.example.chatgpt_api.Retrofit.ChatGPTRequest
import com.example.keyword_miner.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {


    @POST("completions")
    fun getRelKwdStat(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Header("Content-Type: application/json")
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("timeUnit") timeUnit: String,
        @Query("keywordGroups") keywordGroups: String
}
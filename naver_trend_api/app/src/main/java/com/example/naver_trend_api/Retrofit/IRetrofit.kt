package com.example.keyword_miner.Retrofit


import com.example.chatgpt_api.Retrofit.ChatGPTRequest
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {


    @POST("search")
    fun getRelKwdStat(
        @Header("Content-Type") content_type: String,
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Body request: ChatGPTRequest
    ):Call<JsonElement>


}
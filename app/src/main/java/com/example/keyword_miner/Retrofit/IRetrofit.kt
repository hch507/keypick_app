package com.example.keyword_miner.Retrofit

import com.example.keyword_miner.RelKwdStatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface IRetrofit {
    @GET("keywordstool")
    fun getRelKwdStat(
        @Header("X-Naver-Client-Id") client_id : String,
        @Header("X-Naver-Client-Secret") client_secret : String,
        @Query("hintKeywords") hintKeywords: String,
        @Query("showDetail") showDetail: Int = 1,
        @Query("grouping") grouping: Int = 1,
        @Query("month") month: String? = null
    ): Call<RelKwdStatResponse>
}
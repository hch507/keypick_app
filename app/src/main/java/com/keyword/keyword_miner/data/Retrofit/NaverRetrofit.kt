package com.keyword.keyword_miner.data.Retrofit

import com.google.gson.JsonElement
import com.keyword.keyword_miner.data.dto.monthRatio.Result
import com.keyword.keyword_miner.domain.Model.BlogKeywordParam
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NaverRetrofit {
    @POST("search")
    suspend fun getKeywordData(
        @Header("Content-Type") content_type: String,
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Body request: BlogKeywordParam
    ): Response<Result>
}
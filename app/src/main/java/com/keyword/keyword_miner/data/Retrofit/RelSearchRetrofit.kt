package com.keyword.keyword_miner.data.Retrofit

import com.google.gson.JsonElement
import com.keyword.keyword_miner.data.dto.relKeyword.RelKeyword
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RelSearchRetrofit {
    @Headers("Cache-Control: no-cache", "Pragma: no-cache")
    @GET("keywordstool")
    suspend fun getRelKwdStatTest(
        @Header("Content-Type") content_type : String,
        @Header("X-Timestamp") x_timestamp : String,
        @Header("X-API-KEY") api_key : String,
        @Header("X-Customer") x_customer: String,
        @Header("X-Signature") x_signature : String,
        @Query("hintKeywords") hintKeywords: String?,
        @Query("showDetail") showDetail: Int = 1,
    ): Response<RelKeyword>

}
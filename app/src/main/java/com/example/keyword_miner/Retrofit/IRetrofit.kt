package com.example.keyword_miner.Retrofit


import com.example.keyword_miner.Model.BlogKeywordParam
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {
    @GET("keywordstool")
    fun getRelKwdStat(
        @Header("Content-Type") content_type : String,
        @Header("X-Timestamp") x_timestamp : String,
        @Header("X-API-KEY") api_key : String,
        @Header("X-Customer") x_customer: String,
        @Header("X-Signature") x_signature : String,
        @Query("hintKeywords") hintKeywords: String?,
        @Query("showDetail") showDetail: Int = 1,

    ): Call<JsonElement>

    @POST("search")
    fun getKeywordData(
        @Header("Content-Type") content_type: String,
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Body request: BlogKeywordParam
    ):Call<JsonElement>

    @GET("search/blog.json")
    fun getBlogCnt(
        @Header("X-Naver-Client-Id") client_id: String,
        @Header("X-Naver-Client-Secret") client_secret: String,
        @Query("display") display: Int,
        @Query("query") searhTerm: String?,
        @Query("sort") sort: String
    ):Call<JsonElement>

    @GET("nid/me")
    fun getUserBlog(
        @Header("Authorization") authorization: String,
    ):Call<JsonElement>

    @GET
    fun getBlogData(
        @Query("blogId") blogId : String
    ):Call<JsonElement>
}
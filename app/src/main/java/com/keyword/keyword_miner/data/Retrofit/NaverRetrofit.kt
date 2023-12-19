package com.keyword.keyword_miner.data.Retrofit

import com.keyword.keyword_miner.data.dto.blogTotal.BlogTotal
import com.keyword.keyword_miner.data.dto.monthRatio.BlogMonthRatio
import com.keyword.keyword_miner.data.dto.BlogKeywordParam
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface NaverRetrofit {
    @POST("datalab/search")
    suspend fun getKeywordData(
        @Header("Content-Type") content_type: String,
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Body request: BlogKeywordParam
    ): Response<BlogMonthRatio>

    @GET("search/blog.json")
    suspend fun getBlogTotal(
        @Header("X-Naver-Client-Id") client_id: String,
        @Header("X-Naver-Client-Secret") client_secret: String,
        @Query("display") display: Int,
        @Query("query") searhTerm: String?,
        @Query("sort") sort: String
    ):Response<BlogTotal>
}
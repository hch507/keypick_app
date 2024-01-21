package com.keyword.keyword_miner.data.Retrofit

import com.keyword.keyword_miner.data.dto.userBlog.UserBlog
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogRetrofit {

    @GET("NVisitorgp4Ajax.nh")
    suspend fun getBlogData(
        @Query("blogId") blogId : String
    ): Response<UserBlog>
}
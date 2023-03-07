package com.example.keyword_miner.Retrofit



import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {

    @GET("nid/me")
    fun getBlogCnt(
        @Header("Authorization") authorization: String,
    ):Call<JsonElement>


    @GET
    fun getMyBlog(
        @Query("blogId") blogId : String
    ):Call<JsonElement>
}
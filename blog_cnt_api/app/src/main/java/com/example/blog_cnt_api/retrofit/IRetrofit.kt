package com.example.unsplash_app_tutorial.retrofit


import android.icu.number.NumberFormatter.DecimalSeparatorDisplay
import com.example.unsplash_app_tutorial.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface IRetrofit {

    //https://unsplash.com/searchphotos/?query="searchTerm"
    @GET("search/blog.json")
    fun searchPhotos(
        @Header("X-Naver-Client-Id") client_id: String,
        @Header("X-Naver-Client-Secret") client_secret: String,
        @Query("display") display: Int,
        @Query("query") searhTerm: String?,
        @Query("sort") sort: String
    ):Call<JsonElement>
}


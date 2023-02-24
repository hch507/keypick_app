package com.example.keyword_miner.Retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    private  var retrofitClient : Retrofit? = null


    fun getRetrifitClient(base_url:String): Retrofit?{
        retrofitClient = Retrofit.Builder()
            .baseUrl(base_url)

            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitClient
    }

}
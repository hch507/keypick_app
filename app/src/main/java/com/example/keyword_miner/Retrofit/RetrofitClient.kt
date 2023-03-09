package com.example.keyword_miner.Retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private  var retrofitClient : Retrofit? = null



    val gson : Gson = GsonBuilder()
        .setLenient()
        .create()

    fun getRetrifitClient(base_url:String): Retrofit?{
        retrofitClient = Retrofit.Builder()
            .baseUrl(base_url)

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofitClient
    }
    fun getGsonRetrifitClient(base_url:String): Retrofit?{
        retrofitClient = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        return retrofitClient
    }

}
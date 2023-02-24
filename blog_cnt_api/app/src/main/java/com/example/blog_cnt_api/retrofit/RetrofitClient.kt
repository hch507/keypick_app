package com.example.unsplash_app_tutorial.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//싱글톤
object RetrofitClient {
    //레트로핏 클라이언트 선언
    private var retrofitClient : Retrofit? =null

    fun getClient(baseUrl:String):Retrofit?{

        if(retrofitClient==null){

            retrofitClient=Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }

}
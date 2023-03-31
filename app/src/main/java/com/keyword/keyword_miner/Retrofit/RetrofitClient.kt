package com.keyword.keyword_miner.Retrofit

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private  var retrofitClient : Retrofit? = null

    val gson : Gson = GsonBuilder()
        .setLenient()
        .create()


    fun getRetrifitClient(base_url:String): Retrofit?{
        val client = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{

            override fun log(message: String) {
                Log.d("HHHH", "RetrofitClient - log() called / message: $message")

            }

        })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가한다.
        client.addInterceptor(loggingInterceptor)
        retrofitClient = Retrofit.Builder()
            .baseUrl(base_url)
            .client(client.build())
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
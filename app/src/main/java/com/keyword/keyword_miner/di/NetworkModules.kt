package com.keyword.keyword_miner.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.keyword.keyword_miner.data.Retrofit.BlogRetrofit
import com.keyword.keyword_miner.data.Retrofit.NaverRetrofit
import com.keyword.keyword_miner.data.Retrofit.RelSearchRetrofit
import com.keyword.keyword_miner.utils.API
import com.keyword.keyword_miner.utils.MY_BLOG
import com.keyword.keyword_miner.utils.Search_API
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModules {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RelKeywordRetrofit


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverApiRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverBlogRetrofit
    @Provides
    @Singleton
    @NaverBlogRetrofit
    fun provideLoginRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d("HTTP", "RetrofitClient - log() called / message: $message")
            }
        })
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        val retrofitClient = Retrofit.Builder()
            .baseUrl(MY_BLOG.MY_BASE_URL)
            .client(client.build())
            .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
            .build()
        return retrofitClient
    }


    @Provides
    @Singleton
    fun provideLoginApiService(@NaverBlogRetrofit retrofit: Retrofit): BlogRetrofit =
        retrofit.create(BlogRetrofit::class.java)


    @Provides
    @Singleton
    @RelKeywordRetrofit
    fun provideRelRetrofit(): Retrofit {

        val retrofitClient = Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitClient
    }

    @Provides
    @Singleton
    fun provideRelApiService(@RelKeywordRetrofit retrofit: Retrofit): RelSearchRetrofit =
        retrofit.create(RelSearchRetrofit::class.java)


    @Provides
    @Singleton
    @NaverApiRetrofit
    fun provideMonthRatioRetrofit(): Retrofit {
        val retrofitClient = Retrofit.Builder()
            .baseUrl(Search_API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitClient
    }

    @Provides
    @Singleton
    fun provideMonthRatioApiService(@NaverApiRetrofit retrofit: Retrofit): NaverRetrofit =
        retrofit.create(NaverRetrofit::class.java)

}
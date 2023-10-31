package com.keyword.keyword_miner.di

import com.keyword.keyword_miner.data.Retrofit.IRetrofit
import com.keyword.keyword_miner.utils.API
import com.keyword.keyword_miner.utils.MY_BLOG.MY_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
    annotation class BlogCntRetrofit
    @Provides
    @Singleton
    @BlogCntRetrofit
    fun provideLoginRetrofit(): Retrofit {
        val retrofitClient = Retrofit.Builder()
            .baseUrl(MY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitClient
    }

    @Provides
    @Singleton
    fun provideLoginApiService(@BlogCntRetrofit retrofit: Retrofit): IRetrofit = retrofit.create(IRetrofit::class.java)


    @Provides
    @Singleton
    @RelKeywordRetrofit
    fun provideRelRetrofit():Retrofit{
        val  retrofitClient = Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitClient
    }

    @Provides
    @Singleton
    fun provideRelApiService(@RelKeywordRetrofit retrofit: Retrofit):IRetrofit= retrofit.create(IRetrofit::class.java)




}
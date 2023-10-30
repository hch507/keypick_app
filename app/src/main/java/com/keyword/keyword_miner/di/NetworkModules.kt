package com.keyword.keyword_miner.di

import com.keyword.keyword_miner.data.Retrofit.IRetrofit
import com.keyword.keyword_miner.utils.MY_BLOG.MY_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModules {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val retrofitClient = Retrofit.Builder()
            .baseUrl(MY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitClient
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): IRetrofit = retrofit.create(IRetrofit::class.java)
}
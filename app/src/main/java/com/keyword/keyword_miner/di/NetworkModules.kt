package com.keyword.keyword_miner.di

import com.keyword.keyword_miner.data.Retrofit.IRetrofit
import com.keyword.keyword_miner.data.Retrofit.NaverRetrofit
import com.keyword.keyword_miner.data.Retrofit.RelSearchRetrofit
import com.keyword.keyword_miner.utils.API
import com.keyword.keyword_miner.utils.MY_BLOG.MY_BASE_URL
import com.keyword.keyword_miner.utils.Search_API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    annotation class BlogCntRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverApiRetrofit



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
    fun provideLoginApiService(@BlogCntRetrofit retrofit: Retrofit): IRetrofit =
        retrofit.create(IRetrofit::class.java)


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
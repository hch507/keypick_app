//package com.keyword.keyword_miner.di
//
//import android.app.Application
//import android.content.SharedPreferences
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ViewModelComponent
//
//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class PreferenceModule {
//
//    @Provides
//    fun provideSharedPreferences(context: Application) : SharedPreferences{
//        return context.getSharedPreferences("Login", 0)
//    }
//}
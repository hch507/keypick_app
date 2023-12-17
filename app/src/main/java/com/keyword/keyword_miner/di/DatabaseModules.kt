package com.keyword.keyword_miner.di

import android.content.Context
import androidx.room.Room
import com.keyword.keyword_miner.data.local.Room.Roomhelper
import com.keyword.keyword_miner.data.local.Room.RoomhelperHilt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModules {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(
            context.applicationContext,
            Roomhelper::class.java,
            "room_db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(appDatabase : RoomhelperHilt) = appDatabase.roomDao()



}
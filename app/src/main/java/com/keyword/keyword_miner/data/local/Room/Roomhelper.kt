package com.keyword.keyword_miner.data.local.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.keyword.keyword_miner.data.dao.RoomRepositoryDao
import com.keyword.keyword_miner.data.dto.KeywordSaveModel

@Database(entities = arrayOf(KeywordSaveModel::class), version = 1, exportSchema = false)
abstract class Roomhelper : RoomDatabase() {
    abstract fun roomDao(): RoomRepositoryDao

    companion object {
        private var instance: Roomhelper? = null

        @Synchronized
        fun getInstance(context: Context): Roomhelper? {
            if (instance == null) {
                synchronized(Roomhelper::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Roomhelper::class.java,
                        "room_db"
                    )
                        .build()
                }
            }
            return instance
        }
    }
}
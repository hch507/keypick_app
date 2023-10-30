package com.keyword.keyword_miner.data.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.keyword.keyword_miner.data.Repository.RepositoryItem

@Database(entities = arrayOf(RepositoryItem::class), version = 1, exportSchema = false)
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
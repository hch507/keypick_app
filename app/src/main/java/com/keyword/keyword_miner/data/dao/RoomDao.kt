package com.keyword.keyword_miner.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.keyword.keyword_miner.data.dto.KeywordSaveModel

interface RoomDao {
    @Query("SELECT * FROM room_repository")
    suspend fun getAll():List<KeywordSaveModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item : KeywordSaveModel)
    @Delete
    suspend fun delate(item: KeywordSaveModel)
}
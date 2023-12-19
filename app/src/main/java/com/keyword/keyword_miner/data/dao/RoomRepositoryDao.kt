package com.keyword.keyword_miner.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.keyword.keyword_miner.data.dto.KeywordSaveModel

@Dao
interface RoomRepositoryDao {
    @Query("SELECT * FROM room_repository")
    fun getAll():List<KeywordSaveModel>

    @Insert(onConflict = REPLACE)
    fun insert(item : KeywordSaveModel)
    @Delete
    fun delate(item: KeywordSaveModel)
}
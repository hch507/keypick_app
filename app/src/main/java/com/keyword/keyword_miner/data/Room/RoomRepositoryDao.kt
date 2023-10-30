package com.keyword.keyword_miner.data.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.keyword.keyword_miner.data.Repository.RepositoryItem

@Dao
interface RoomRepositoryDao {

    @Query("SELECT * FROM room_repository")
    fun getAll():List<RepositoryItem>

    @Insert(onConflict = REPLACE)
    fun insert(item : RepositoryItem)
    @Delete
    fun delate(item: RepositoryItem)
}
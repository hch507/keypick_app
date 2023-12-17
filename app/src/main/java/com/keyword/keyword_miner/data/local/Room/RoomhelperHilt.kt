package com.keyword.keyword_miner.data.local.Room

import androidx.room.Database
import com.keyword.keyword_miner.data.dao.RoomDao
import com.keyword.keyword_miner.data.dto.KeywordSaveModel


@Database(entities = [KeywordSaveModel::class], version = 1, exportSchema = false)
abstract class RoomhelperHilt {
    abstract fun roomDao() : RoomDao
}
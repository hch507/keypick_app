package com.example.keyword_miner.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_repository")
data class RepositoryItem (
    @PrimaryKey
    var keyword: String ="",
    @ColumnInfo
    var monthCnt: String ="",
    @ColumnInfo
    var blogCnt: String ="",
    @ColumnInfo
    var datetime: Long? = null

)

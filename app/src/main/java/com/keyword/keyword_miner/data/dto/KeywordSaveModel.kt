package com.keyword.keyword_miner.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_repository")
data class KeywordSaveModel (
    @PrimaryKey
    var keyword: String ="",
    @ColumnInfo
    var monthCnt: String ="",
    @ColumnInfo
    var blogCnt: String ="",
    @ColumnInfo
    var datetime: String? = ""

)

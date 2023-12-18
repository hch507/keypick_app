package com.keyword.keyword_miner.domain.repository

import com.keyword.keyword_miner.data.dto.KeywordSaveModel

interface RoomRepository {

    suspend fun getData() : List<KeywordSaveModel>

    suspend fun InsertData(saveData: KeywordSaveModel)

    suspend fun DeleteData()
}
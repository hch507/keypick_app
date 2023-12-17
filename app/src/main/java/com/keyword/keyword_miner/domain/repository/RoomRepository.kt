package com.keyword.keyword_miner.domain.repository

interface RoomRepository {

    suspend fun getData()

    suspend fun InsertData()

    suspend fun DeleteData()
}
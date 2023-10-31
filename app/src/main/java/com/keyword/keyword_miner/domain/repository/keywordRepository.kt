package com.keyword.keyword_miner.domain.repository

interface keywordRepository {

    suspend fun getKeywordRel(searchTerm : String)

    suspend fun getMonthRatio()
}
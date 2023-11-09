package com.keyword.keyword_miner.domain.repository

interface KeywordRepository {

    suspend fun getKeywordRel(searchTerm : String)

    suspend fun getMonthRatio(searchTerm: String)
}
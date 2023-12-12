package com.keyword.keyword_miner.domain.repository


import com.keyword.keyword_miner.domain.Model.blogTotalData.BlogTotalDataModel
import com.keyword.keyword_miner.domain.Model.monthRadioData.MonthRatioDataModel
import com.keyword.keyword_miner.domain.Model.relKeywordData.RelKeywordDataModel

interface KeywordRepository {
    suspend fun getKeywordRel(searchTerm : String) : List<RelKeywordDataModel>?

    suspend fun getMonthRatio(searchTerm: String) :List<MonthRatioDataModel>?

    suspend fun getBlogTotal(searchTerm: String) : BlogTotalDataModel?

    suspend fun getBlogRank(searchterm : String)
}
package com.keyword.keyword_miner.domain.repository


import com.keyword.keyword_miner.domain.model.blogTotalData.BlogTotalDataModel
import com.keyword.keyword_miner.domain.model.monthRadioData.MonthRatioDataModel
import com.keyword.keyword_miner.domain.model.rankData.RankDataModel
import com.keyword.keyword_miner.domain.model.relkeyworddata.RelKeywordDataModel
import kotlinx.coroutines.flow.Flow

interface KeywordRepository {
    suspend fun getKeywordRel(searchTerm : String) : Flow<List<RelKeywordDataModel>?>

    suspend fun getMonthRatio(searchTerm: String) :Flow<List<MonthRatioDataModel>?>

    suspend fun getBlogTotal(searchTerm: String) : Flow<BlogTotalDataModel?>

    suspend fun getBlogRank(searchterm : String) : Flow<RankDataModel?>
}
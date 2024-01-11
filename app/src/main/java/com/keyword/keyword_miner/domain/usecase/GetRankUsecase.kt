package com.keyword.keyword_miner.domain.usecase

import com.keyword.keyword_miner.domain.model.rankData.RankDataModel
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import javax.inject.Inject

class GetRankUsecase @Inject constructor(
    private val keywordRepository: KeywordRepository
){
    operator suspend fun invoke(searchTerm : String) : RankDataModel? = keywordRepository.getBlogRank(searchTerm)
}
package com.keyword.keyword_miner.domain.usecase

import android.util.Log
import com.keyword.keyword_miner.domain.Model.relKeywordData.RelKeywordDataModel
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import javax.inject.Inject

class GetRelKeywordUsecase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    operator suspend fun invoke(searchTerm: String) : List<RelKeywordDataModel>? = keywordRepository.getKeywordRel(searchTerm)
}
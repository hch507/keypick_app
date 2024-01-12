package com.keyword.keyword_miner.domain.usecase

import com.keyword.keyword_miner.domain.model.relkeyworddata.RelKeywordDataModel
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRelKeywordUsecase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    operator suspend fun invoke(searchTerm: String) : Flow<List<RelKeywordDataModel>?> = keywordRepository.getKeywordRel(searchTerm)
}
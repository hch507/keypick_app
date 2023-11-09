package com.keyword.keyword_miner.domain.usecase

import com.keyword.keyword_miner.domain.repository.KeywordRepository
import javax.inject.Inject

class getMonthRatioUsecase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {

    operator suspend fun invoke(searchTerm : String){
        return keywordRepository.getMonthRatio(searchTerm)
    }
}
package com.keyword.keyword_miner.domain.usecase

import android.util.Log
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import javax.inject.Inject

class getRelKeywordUsecase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    operator suspend fun invoke(searchTerm : String){
        Log.d("hch", "invoke: ")
        return keywordRepository.getKeywordRel(searchTerm)
    }

}
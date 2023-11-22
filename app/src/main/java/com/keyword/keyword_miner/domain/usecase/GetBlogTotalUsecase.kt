package com.keyword.keyword_miner.domain.usecase


import android.util.Log

import com.keyword.keyword_miner.domain.Model.blogTotalData.BlogTotalDataModel
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import javax.inject.Inject

class GetBlogTotalUsecase @Inject constructor(
        private val keywordRepository: KeywordRepository
){
    operator suspend fun invoke(searchTerm : String) : BlogTotalDataModel? = keywordRepository.getBlogTotal(searchTerm)
}
package com.keyword.keyword_miner.domain.usecase

import com.keyword.keyword_miner.data.dto.userBlog.UserBlog
import com.keyword.keyword_miner.domain.repository.UserBLogRepository

import javax.inject.Inject

class GetUserBlogUsecase @Inject constructor(
    private val userBlogRepository: UserBLogRepository
){
    operator suspend fun invoke(userId : String) : UserBlog? = userBlogRepository.getUserBlogData(userId)

}
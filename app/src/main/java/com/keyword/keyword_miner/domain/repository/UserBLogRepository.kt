package com.keyword.keyword_miner.domain.repository

import com.keyword.keyword_miner.data.dto.userBlog.UserBlog

interface UserBLogRepository {
    suspend fun getUserBlogData(userId : String) : UserBlog?
}
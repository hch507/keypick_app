package com.keyword.keyword_miner.domain.repository

import com.keyword.keyword_miner.data.dto.userBlog.UserBlog
import javax.inject.Inject

interface LoginRepository {
    suspend fun getIsLogin(userId : String) : UserBlog?

}
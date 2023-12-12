package com.keyword.keyword_miner.data.repositoryImpl

import android.util.Log
import com.keyword.keyword_miner.data.Retrofit.BlogRetrofit
import com.keyword.keyword_miner.data.dto.userBlog.UserBlog
import com.keyword.keyword_miner.domain.repository.UserBLogRepository
import javax.inject.Inject

class UserBlogRepositoryImpl @Inject constructor(
    private val blogApiRetrofit: BlogRetrofit
) : UserBLogRepository{
    override suspend fun getUserBlogData(userId: String): UserBlog? {
        val response = blogApiRetrofit.getBlogData(
            blogId = userId
        )
        val responseBlogCnt  = response.body()
        Log.d("hhh", "userBlogData: ${responseBlogCnt} ")
        return responseBlogCnt
    }

}
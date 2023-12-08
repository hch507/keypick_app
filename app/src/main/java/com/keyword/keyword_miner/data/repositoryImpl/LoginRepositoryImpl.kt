package com.keyword.keyword_miner.data.repositoryImpl


import android.util.Log
import com.keyword.keyword_miner.data.Retrofit.BlogRetrofit
import com.keyword.keyword_miner.data.dto.userBlog.UserBlog
import com.keyword.keyword_miner.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val blogApiRetrofit: BlogRetrofit
) : LoginRepository {
    override suspend fun getIsLogin(userId: String) : UserBlog? {
        val response = blogApiRetrofit.getBlogData(
            blogId = userId
        )
        val responseBlogCnt  = response.body()
        Log.d("hhh", "getIsLogin: ${responseBlogCnt} ")
        return responseBlogCnt
    }


}
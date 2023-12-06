package com.keyword.keyword_miner.domain.repository

import javax.inject.Inject

interface LoginRepository {
    suspend fun getIsLogin(userId : String)
}
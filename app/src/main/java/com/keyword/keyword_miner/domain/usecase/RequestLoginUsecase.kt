package com.keyword.keyword_miner.domain.usecase

import com.keyword.keyword_miner.domain.repository.LoginRepository
import javax.inject.Inject

class RequestLoginUsecase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator suspend fun invoke(userId : String){
        loginRepository.getIsLogin(userId)
    }
}
package com.keyword.keyword_miner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyword.keyword_miner.data.dto.userBlog.UserBlog
import com.keyword.keyword_miner.domain.usecase.RequestLoginUsecase
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val requestLoginUseCase : RequestLoginUsecase
) : ViewModel() {
    private val _currentBlogCnt = MutableStateFlow<MainUiState<UserBlog>>(MainUiState.Loading)
    val currentBlogCnt = _currentBlogCnt.asStateFlow()

    fun requestLogin(userId : String){
        viewModelScope.launch {
            _currentBlogCnt.value = MainUiState.success(requestLoginUseCase.invoke(userId)!!)
        }
    }
}
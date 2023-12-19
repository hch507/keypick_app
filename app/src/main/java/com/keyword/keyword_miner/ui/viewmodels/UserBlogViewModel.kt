package com.keyword.keyword_miner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyword.keyword_miner.data.dto.userBlog.UserBlog
import com.keyword.keyword_miner.data.local.sharePref.PreferenceUtil
import com.keyword.keyword_miner.domain.usecase.GetUserBlogUsecase
import com.keyword.keyword_miner.ui.App
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UserBlogViewModel @Inject constructor(
    val getUserBlogUsecase: GetUserBlogUsecase,
    val prefs : PreferenceUtil
) : ViewModel() {
    private val _currentBlogCnt = MutableStateFlow<MainUiState<UserBlog>>(MainUiState.Loading)
    val currentBlogCnt = _currentBlogCnt.asStateFlow()

    fun getUserBlogData(userId : String){
        viewModelScope.launch {
            _currentBlogCnt.value =getUserBlogUsecase.invoke(userId)?.let { MainUiState.success(it) }?: MainUiState.Error
        }
    }
    fun getUserEmail(): String{
        return prefs.getEmail("userEmail", "")
    }
    fun saveUserEmail(userEmail : String){
        prefs.setEmail("userEmail",userEmail)
    }
}
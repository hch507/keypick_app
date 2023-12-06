package com.keyword.keyword_miner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyword.keyword_miner.domain.usecase.RequestLoginUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val requestLoginUseCase : RequestLoginUsecase
) : ViewModel() {

    fun requestLogin(userId : String){
        viewModelScope.launch {
            requestLoginUseCase.invoke(userId)
        }
    }
}
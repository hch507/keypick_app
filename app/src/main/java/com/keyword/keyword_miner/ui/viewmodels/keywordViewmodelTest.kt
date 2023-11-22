package com.keyword.keyword_miner.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyword.keyword_miner.domain.Model.RelKeywordInfo
import com.keyword.keyword_miner.domain.Model.blogTotalData.BlogTotalDataModel
import com.keyword.keyword_miner.domain.Model.monthRadioData.MonthRatioDataModel
import com.keyword.keyword_miner.domain.Model.relKeywordData.RelKeywordDataModel
import com.keyword.keyword_miner.domain.usecase.GetBlogTotalUsecase
import com.keyword.keyword_miner.domain.usecase.GetMonthRatioUsecase
import com.keyword.keyword_miner.domain.usecase.GetRelKeywordUsecase
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class keywordViewmodelTest @Inject constructor(
    private val relKeywordUsecase: GetRelKeywordUsecase,
    private val monthRatioUsecase: GetMonthRatioUsecase,
    private val blogTotalUsecase: GetBlogTotalUsecase

) : ViewModel() {

    private val _currentRelData = MutableStateFlow<MainUiState<List<RelKeywordDataModel>>>(MainUiState.Loading)
    val currentRelData = _currentRelData.asStateFlow()

    private val _currentMonthRatio = MutableStateFlow<MainUiState<List<MonthRatioDataModel>>>(MainUiState.Loading)
    val currentMonthRatio = _currentMonthRatio.asStateFlow()

    private val _currentBlogTotal = MutableStateFlow<MainUiState<BlogTotalDataModel>>(MainUiState.Loading)
    val currentBlogTotal =_currentBlogTotal.asStateFlow()

    fun getRelData(searchTerm: String) {
        viewModelScope.launch {
            _currentRelData.value = MainUiState.success(relKeywordUsecase.invoke(searchTerm)!!)
        }
    }

    fun getMonthRatioData(searchTerm: String) {
        viewModelScope.launch {
            _currentMonthRatio.value = MainUiState.success(monthRatioUsecase.invoke(searchTerm)!!)
        }
    }

    fun getBlogTotal(searchTerm: String) {
        viewModelScope.launch {
            _currentBlogTotal.value = MainUiState.success(blogTotalUsecase.invoke(searchTerm)!!)
        }
    }
}
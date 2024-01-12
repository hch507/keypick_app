package com.keyword.keyword_miner.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.domain.model.blogTotalData.BlogTotalDataModel
import com.keyword.keyword_miner.domain.model.monthRadioData.MonthRatioDataModel
import com.keyword.keyword_miner.domain.model.relkeyworddata.RelKeywordDataModel
import com.keyword.keyword_miner.domain.usecase.GetBlogTotalUsecase
import com.keyword.keyword_miner.domain.usecase.GetMonthRatioUsecase
import com.keyword.keyword_miner.domain.usecase.GetRelKeywordUsecase
import com.keyword.keyword_miner.domain.usecase.InsertKeywordUsecase
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(
    private val relKeywordUsecase: GetRelKeywordUsecase,
    private val monthRatioUsecase: GetMonthRatioUsecase,
    private val blogTotalUsecase: GetBlogTotalUsecase,
    private val insertKeywordUsecase: InsertKeywordUsecase

) : ViewModel() {

    private val _currentRelData = MutableStateFlow<MainUiState<List<RelKeywordDataModel>>>(MainUiState.Loading)
    val currentRelData = _currentRelData.asStateFlow()

    private val _currentMonthRatio = MutableStateFlow<MainUiState<List<MonthRatioDataModel>>>(MainUiState.Loading)
    val currentMonthRatio = _currentMonthRatio.asStateFlow()

    private val _currentBlogTotal = MutableStateFlow<MainUiState<BlogTotalDataModel>>(MainUiState.Loading)
    val currentBlogTotal =_currentBlogTotal.asStateFlow()

    fun getRelData(searchTerm: String) {
        viewModelScope.launch {
            relKeywordUsecase.invoke(searchTerm)
                .onStart { _currentRelData.update { MainUiState.Loading } }
                .catch { _currentRelData.update { MainUiState.Error } }
                .collectLatest { value -> _currentRelData.update{MainUiState.success(value!!)}  }
        }
    }

    fun getMonthRatioData(searchTerm: String) {
        viewModelScope.launch {
            monthRatioUsecase.invoke(searchTerm)
                .onStart { _currentMonthRatio.update{MainUiState.Loading} }
                .catch { _currentMonthRatio.update { MainUiState.Error } }
                .collectLatest { value -> _currentMonthRatio.update { MainUiState.success(value!!) } }

        }
    }

    fun getBlogTotal(searchTerm: String) {
        viewModelScope.launch {
            blogTotalUsecase.invoke(searchTerm)
                .onStart { _currentBlogTotal.update{MainUiState.Loading} }
                .catch { _currentBlogTotal.update { MainUiState.Error } }
                .collectLatest { value -> _currentBlogTotal.update { MainUiState.success(value!!) } }
        }
    }

    fun insertKeyword(keywordData : KeywordSaveModel){
        viewModelScope.launch {
            Log.d("Insert", " Viewmodel insertKeyword: ")
            insertKeywordUsecase.invoke(keywordData)
        }
    }
}
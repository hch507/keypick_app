package com.keyword.keyword_miner.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyword.keyword_miner.domain.Model.RelKeywordInfo
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import com.keyword.keyword_miner.domain.usecase.getMonthRatioUsecase
import com.keyword.keyword_miner.domain.usecase.getRelKeywordUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class keywordViewmodelTest @Inject constructor(
    private val relKeywordUsecase: getRelKeywordUsecase,
    private val monthRatioUsecase: getMonthRatioUsecase,

): ViewModel() {
    private val _currentRelData = MutableLiveData <List<RelKeywordInfo>>()
//    val currentRelData : LiveData<List<RelKeywordInfo>>
//        get() = _currentRelData

    fun getRelData(searchTerm : String){
        viewModelScope.launch {
            Log.d("hch", "getRelData: ")
            relKeywordUsecase.invoke(searchTerm)
        }
    }
    fun getMonthRatioData(searchTerm: String) {
        viewModelScope.launch {
            monthRatioUsecase.invoke(searchTerm)
        }
    }
}
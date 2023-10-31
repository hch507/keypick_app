package com.keyword.keyword_miner.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyword.keyword_miner.KeywordInfo
import com.keyword.keyword_miner.domain.Model.ItemPeriod
import com.keyword.keyword_miner.domain.Model.blogData
import com.keyword.keyword_miner.domain.repository.keywordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class keywordViewmodelTest @Inject constructor(
    private val keywordRepository: keywordRepository
): ViewModel() {
    private val _currentRelData = MutableLiveData <ArrayList<KeywordInfo>>()
    val currentRelData : LiveData<ArrayList<KeywordInfo>>
        get() = _currentRelData

    private val _currentBlogDate = MutableLiveData<ArrayList<ItemPeriod>>()
    val currentBlogDate : LiveData<ArrayList<ItemPeriod>>
        get() = _currentBlogDate

    private val _currentMonthCnt = MutableLiveData<ArrayList<blogData>>()
    val currentMonthCnt : LiveData<ArrayList<blogData>>
        get() = _currentMonthCnt

    fun getRelData(se){
        viewModelScope.launch {
            keywordRepository.getKeywordRel()
        }
    }
    fun getMonthRatioData(){
        viewModelScope.launch {
            keywordRepository.getMonthRatio()
        }
    }
}
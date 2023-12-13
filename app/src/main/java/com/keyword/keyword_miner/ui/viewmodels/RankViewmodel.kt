package com.keyword.keyword_miner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyword.keyword_miner.domain.Model.rankData.RankDataModel
import com.keyword.keyword_miner.domain.usecase.GetRankUsecase
import com.keyword.keyword_miner.ui.App
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankViewmodel @Inject constructor(
    private val getRankUsecase: GetRankUsecase
) : ViewModel(){
    private var _currentRank = MutableStateFlow<MainUiState<RankDataModel>>(MainUiState.Loading)
    var currentRank = _currentRank.asStateFlow()
    fun getBlogRank(searchTerm : String){
        viewModelScope.launch {
            _currentRank.value = MainUiState.success(getRankUsecase.invoke(searchTerm)!!)
        }

    }

    fun getUserEmail(): String{
        return  App.prefs.getEmail("userEmail", "")
    }
}
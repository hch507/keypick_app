package com.keyword.keyword_miner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyword.keyword_miner.data.local.sharePref.PreferenceUtil
import com.keyword.keyword_miner.domain.model.rankData.RankDataModel
import com.keyword.keyword_miner.domain.usecase.GetRankUsecase
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankViewmodel @Inject constructor(
    private val getRankUsecase: GetRankUsecase,
    val prefs : PreferenceUtil

) : ViewModel(){
    private var _currentRank = MutableStateFlow<MainUiState<RankDataModel>>(MainUiState.Loading)
    var currentRank = _currentRank.asStateFlow()
    fun getBlogRank(searchTerm : String){
        viewModelScope.launch {
            _currentRank.value = MainUiState.success(getRankUsecase.invoke(searchTerm)!!)
        }

    }

    fun getUserEmail(): String{
        return prefs.getEmail("userEmail", "")
    }
}
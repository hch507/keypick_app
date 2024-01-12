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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
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
            viewModelScope.launch {
                getRankUsecase.invoke(searchTerm)
                    .onStart { _currentRank.update{MainUiState.Loading} }
                    .catch { _currentRank.update { MainUiState.Error } }
                    .collectLatest { value -> _currentRank.update { MainUiState.success(value!!) } }
            }
        }

    }

    fun getUserEmail(): String{
        return prefs.getEmail("userEmail", "")
    }
}
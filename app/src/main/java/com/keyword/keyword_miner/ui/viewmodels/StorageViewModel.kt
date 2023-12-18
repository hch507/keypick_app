package com.keyword.keyword_miner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.domain.repository.RoomRepository
import com.keyword.keyword_miner.domain.usecase.GetSavedKeywordUsecase
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private var getSavedKeywordUsecase: GetSavedKeywordUsecase
)
:ViewModel() {

    private var _savedData = MutableStateFlow<MainUiState<List<KeywordSaveModel>>>(MainUiState.Loading)
    var savedData = _savedData.asStateFlow()

    fun getSavedData(){
        viewModelScope.launch {
            _savedData.value=MainUiState.success(getSavedKeywordUsecase.invoke())
        }
    }
}
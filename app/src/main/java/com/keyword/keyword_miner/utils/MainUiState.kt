package com.keyword.keyword_miner.utils

sealed class MainUiState<out T>(val _data : T?){
    object Loading : MainUiState<Nothing>(_data = null)
    object Error : MainUiState<Nothing>(_data = null)
    data class success<out T>(val data : T) : MainUiState<T>(_data = data)
}

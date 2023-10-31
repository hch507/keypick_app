package com.keyword.keyword_miner.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.data.Room.Roomhelper
import com.keyword.keyword_miner.ui.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryViewModel:ViewModel() {
    val context: Context = App.instance.getAppContext()
    var helper: Roomhelper = Roomhelper.getInstance(context)!!

    private val _currentReposioty = MutableLiveData <List<KeywordSaveModel>>()
    val currentRepository : LiveData<List<KeywordSaveModel>>
        get() = _currentReposioty

    fun loadRepository(){
        CoroutineScope(Dispatchers.IO).launch {
            val storeItems = helper.roomDao().getAll()

            withContext(Dispatchers.Main) {
                _currentReposioty.value = storeItems
            }
        }
    }
    fun deleteItem(item: KeywordSaveModel){
        CoroutineScope(Dispatchers.IO).launch {
            helper.roomDao().delate(item)
            val storeItems = helper.roomDao().getAll()

            withContext(Dispatchers.Main) {
                _currentReposioty.value = storeItems
            }
        }
    }

}
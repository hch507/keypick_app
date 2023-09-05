package com.keyword.keyword_miner.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keyword.keyword_miner.Room.Roomhelper
import com.keyword.keyword_miner.sharePref.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryViewModel:ViewModel() {
    val context: Context = App.instance.getAppContext()
    var helper: Roomhelper=Roomhelper.getInstance(context)!!

    private val _currentReposioty = MutableLiveData <List<RepositoryItem>>()
    val currentRepository : LiveData<List<RepositoryItem>>
        get() = _currentReposioty

    fun loadRepository(){
        CoroutineScope(Dispatchers.IO).launch {
            val storeItems = helper.roomDao().getAll()
//            Log.d("HCH", "RepositoryFragment-getAll() called${storeItems}")
            withContext(Dispatchers.Main) {
                _currentReposioty.value = storeItems
                Log.d("HCH", "RepositoryFragment-getAll() called${_currentReposioty}")
            }
        }
    }
    fun deleteItem(item: RepositoryItem){
        CoroutineScope(Dispatchers.IO).launch {
            helper.roomDao().delate(item)
            val storeItems = helper.roomDao().getAll()
//            Log.d("HCH", "RepositoryFragment-getAll() called${storeItems}")
            withContext(Dispatchers.Main) {
                _currentReposioty.value = storeItems
                Log.d("HCH", "RepositoryFragment-getAll() called${_currentReposioty}")
            }
        }
    }

}
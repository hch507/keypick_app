package com.example.keyword_miner.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.keyword_miner.KeywordInfo
import com.example.keyword_miner.Room.Roomhelper
import com.example.keyword_miner.sharePref.App
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
            Log.d("HCH", "RepositoryFragment-getAll() called${storeItems}")

            _currentReposioty.value=storeItems
        }
    }
}
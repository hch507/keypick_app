package com.example.keyword_miner.Repository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.keyword_miner.RecyclerView.RepositoryRecyclerViewAdapter
import com.example.keyword_miner.Room.Roomhelper
import com.example.keyword_miner.databinding.ActivityRepositoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryActivity : AppCompatActivity() {
    lateinit var binding : ActivityRepositoryBinding
    lateinit var helper: Roomhelper
    lateinit var keywordAdapter: RepositoryRecyclerViewAdapter
    var storeItemList =mutableListOf<RepositoryItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helper= Roomhelper.getInstance(this)!!

        Log.d("HCH", "RepositoryFragment-onCreateView() called")
        keywordAdapter= RepositoryRecyclerViewAdapter()
//        keywordAdapter.submit(storeItemList)
        storeItemList = mutableListOf()
        CoroutineScope(Dispatchers.IO).launch {
            val storeItems = helper.roomDao().getAll()
            Log.d("HCH", "RepositoryFragment-getAll() called${storeItems}")
            storeItemList.addAll(storeItems)
            withContext(Dispatchers.Main) {
                keywordAdapter.submit(storeItemList)
                binding.repositoryRecyclerview.layoutManager = LinearLayoutManager(this@RepositoryActivity,
                    LinearLayoutManager.VERTICAL, false)
                binding.repositoryRecyclerview.adapter = keywordAdapter
            }
        }
    }
}
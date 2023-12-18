package com.keyword.keyword_miner.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyword.keyword_miner.databinding.ActivityRepositoryBinding
import com.keyword.keyword_miner.RecyclerView.RepositoryRecyclerViewAdapter
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.data.local.Room.Roomhelper
import com.keyword.keyword_miner.ui.viewmodels.RepositoryViewModel
import com.keyword.keyword_miner.ui.viewmodels.StorageViewModel
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryActivity : AppCompatActivity() {
    lateinit var binding : ActivityRepositoryBinding
    lateinit var keywordAdapter: RepositoryRecyclerViewAdapter
    var storeItemList =listOf<KeywordSaveModel>()
    val storageViewModel : StorageViewModel by viewModels()
    val repositoryViewModel: RepositoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        keywordAdapter= RepositoryRecyclerViewAdapter(storageViewModel)


        storageViewModel.getSavedData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                storageViewModel.savedData.collectLatest {
                    when(it){
                        is MainUiState.success ->{
                            Log.d("Storage", "onCreate: ${it.data}")
                            storeItemList = it.data
                            keywordAdapter.submit(storeItemList)
                            binding.repositoryRecyclerview.apply {
                                layoutManager =LinearLayoutManager(this@RepositoryActivity, LinearLayoutManager.VERTICAL, false)
                                adapter =keywordAdapter
                            }
                        }
                        is MainUiState.Error -> {
                            Toast.makeText(
                                this@RepositoryActivity,
                                "블로그 아이디를 다시 확인해주세요",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is MainUiState.Loading -> {}
                    }
                }
            }
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}
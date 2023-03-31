package com.keyword.keyword_miner.Repository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyword.keyword_miner.databinding.ActivityRepositoryBinding
import com.keyword.keyword_miner.RecyclerView.RepositoryRecyclerViewAdapter
import com.keyword.keyword_miner.Room.Roomhelper


class RepositoryActivity : AppCompatActivity() {
    lateinit var binding : ActivityRepositoryBinding
    lateinit var helper: Roomhelper
    lateinit var keywordAdapter: RepositoryRecyclerViewAdapter
    var storeItemList =listOf<RepositoryItem>()

    val repositoryViewModel: RepositoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        helper= Roomhelper.getInstance(this)!!

        Log.d("HCH", "RepositoryFragment-onCreateView() called")
        keywordAdapter= RepositoryRecyclerViewAdapter(repositoryViewModel)
//        keywordAdapter.submit(storeItemList)

 //       storeItemList = mutableListOf()
        repositoryViewModel.loadRepository()

        repositoryViewModel.currentRepository.observe(this) { repositoryItem ->
            this.storeItemList = repositoryItem
            keywordAdapter.submit(storeItemList)
            binding.repositoryRecyclerview.layoutManager = LinearLayoutManager(
                this@RepositoryActivity,
                LinearLayoutManager.VERTICAL, false
            )
            binding.repositoryRecyclerview.adapter = keywordAdapter
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
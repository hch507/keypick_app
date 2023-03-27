package com.example.keyword_miner.RecyclerView

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.keyword_miner.BlogIdActivity
import com.example.keyword_miner.Repository.RepositoryItem
import com.example.keyword_miner.Repository.RepositoryViewModel
import com.example.keyword_miner.databinding.ActivityRepositoryViewBinding
import com.example.keyword_miner.sharePref.App
import com.example.keyword_miner.utils.constant

class RepositoryRecyclerViewAdapter(var viewModel: RepositoryViewModel) : RecyclerView.Adapter<RepositoryViewHolder>() {
    private var RepositoryList = listOf<RepositoryItem>()
    val Context  = App.instance.getAppContext()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        Log.d(constant.TAG, "Adapter-onCreateViewHolder() called")
        val itemhodler= RepositoryViewHolder(ActivityRepositoryViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        return itemhodler
    }

    override fun getItemCount(): Int {
        return this.RepositoryList.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        Log.d(constant.TAG, "Adapter-onBindViewHolder() called")
        holder.bind(this.RepositoryList[position])
        holder.btn.setOnClickListener {

            viewModel.deleteItem(this.RepositoryList[position])
        }
    }
    fun submit(repositoryItem : List<RepositoryItem>){
        Log.d("HCH", "RepositoryRecyclerViewAdapter-submit() called${repositoryItem}")
        this.RepositoryList= repositoryItem
    }
}
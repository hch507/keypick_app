package com.example.keyword_miner.RecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.keyword_miner.KeywordInfo
import com.example.keyword_miner.databinding.ActivityItemViewBinding
import com.example.keyword_miner.utils.constant.TAG

class RelKeywordRecyclerViewAdapter : RecyclerView.Adapter<RelKeywordViewHolder>() {
    private var keywordList = ArrayList<KeywordInfo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelKeywordViewHolder {
        Log.d(TAG, "Adapter-onCreateViewHolder() called")
        val itemhodler= RelKeywordViewHolder(ActivityItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        return itemhodler
    }

    override fun onBindViewHolder(holder: RelKeywordViewHolder, position: Int) {
        Log.d(TAG, "Adapter-onBindViewHolder() called")
        holder.bind(this.keywordList[position])
    }

    override fun getItemCount(): Int {
        return this.keywordList.size
    }
    fun submit(keywordList : ArrayList<KeywordInfo>){
        this.keywordList= keywordList
    }
}
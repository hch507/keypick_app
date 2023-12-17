package com.keyword.keyword_miner.RecyclerView

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

import com.keyword.keyword_miner.databinding.ActivityItemViewBinding
import com.keyword.keyword_miner.domain.Model.relKeywordData.RelKeywordDataModel
import com.keyword.keyword_miner.ui.viewmodels.KeywordViewModel
import com.keyword.keyword_miner.utils.constant.TAG


class RelKeywordRecyclerViewAdapter(var viewModel: KeywordViewModel) : RecyclerView.Adapter<RelKeywordViewHolder>() {
    lateinit var keywordList : List<RelKeywordDataModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelKeywordViewHolder {
        Log.d(TAG, "Adapter-onCreateViewHolder() called")
        val itemhodler= RelKeywordViewHolder(ActivityItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        return itemhodler
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RelKeywordViewHolder, position: Int) {
        Log.d(TAG, "Adapter-onBindViewHolder() called")
        holder.bind(this.keywordList[position])
        holder.itemView.setOnClickListener {
//            viewModel.updateKeywordData(this.keywordList[position].relKeyword)
            viewModel.apply {
                keywordList[position].relKeyword?.let { it1 -> getBlogTotal(it1) }
                keywordList[position].relKeyword?.let { it1 -> getRelData(it1) }
                keywordList[position].relKeyword?.let { it1 -> getMonthRatioData(it1) }
            }
        }
    }

    override fun getItemCount(): Int {
        return this.keywordList.size
    }
    fun submit(keywordList : List<RelKeywordDataModel>){
        this.keywordList= keywordList
    }
}
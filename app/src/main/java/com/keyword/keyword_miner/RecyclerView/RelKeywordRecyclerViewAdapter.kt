package com.keyword.keyword_miner.RecyclerView

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.keyword.keyword_miner.databinding.RelkeywordItemBinding
import com.keyword.keyword_miner.domain.model.relkeyworddata.RelKeywordDataModel
import com.keyword.keyword_miner.ui.fragments.RelFragment
import com.keyword.keyword_miner.utils.constant.TAG


class RelKeywordRecyclerViewAdapter(
    val handler: RelFragment.RelKeywordHandler
) : RecyclerView.Adapter<RelKeywordViewHolder>() {
    lateinit var keywordList: List<RelKeywordDataModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelKeywordViewHolder {
        Log.d(TAG, "Adapter-onCreateViewHolder() called")
         return RelKeywordViewHolder(RelkeywordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
             handler)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RelKeywordViewHolder, position: Int) {
        Log.d(TAG, "Adapter-onBindViewHolder() called")
        holder.bind(this.keywordList[position])
    }

    override fun getItemCount(): Int {
        return this.keywordList.size
    }

    fun submit(keywordList: List<RelKeywordDataModel>) {
        this.keywordList = keywordList
    }
}
package com.keyword.keyword_miner.RecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.keyword.keyword_miner.KeywordInfo
import com.keyword.keyword_miner.databinding.ActivityItemViewBinding

class RelKeywordViewHolder(val binding : ActivityItemViewBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item:KeywordInfo){
        binding.relkeyword.text=item.relKeyword
        binding.mobileCnt.text=item.monthlyMobileQcCnt
        binding.pcCnt.text=item.monthlyPcQcCnt
    }
}
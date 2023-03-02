package com.example.keyword_miner.RecyclerView

import android.content.ClipData.Item
import androidx.recyclerview.widget.RecyclerView
import com.example.keyword_miner.KeywordInfo
import com.example.keyword_miner.databinding.ActivityItemViewBinding

class RelKeywordViewHolder(val binding : ActivityItemViewBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item:KeywordInfo){
        binding.relkeyword.text=item.relKeyword
        binding.mobileCnt.text=item.monthlyMobileQcCnt
        binding.pcCnt.text=item.monthlyPcQcCnt
    }
}
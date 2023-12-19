package com.keyword.keyword_miner.RecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.keyword.keyword_miner.databinding.ActivityItemViewBinding
import com.keyword.keyword_miner.domain.Model.relKeywordData.RelKeywordDataModel

class RelKeywordViewHolder(val binding: ActivityItemViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RelKeywordDataModel) {
        binding.relkeyword.text = item.relKeyword
        binding.mobileCnt.text = item.monthlyMobileQcCnt
        binding.pcCnt.text = item.monthlyPcQcCnt
    }
}
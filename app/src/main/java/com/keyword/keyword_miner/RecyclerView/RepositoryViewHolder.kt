package com.keyword.keyword_miner.RecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.databinding.ActivityRepositoryViewBinding


class RepositoryViewHolder(val binding: ActivityRepositoryViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: KeywordSaveModel) {
        binding.keyword.text = item.keyword
        binding.monthCnt.text = item.monthCnt.toString()
        binding.blogCnt.text = item.blogCnt
        binding.datetime.text = item.datetime

    }

    val btn = binding.itemClear
}
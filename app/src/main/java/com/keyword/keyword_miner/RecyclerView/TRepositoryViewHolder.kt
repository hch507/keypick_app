package com.keyword.keyword_miner.RecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.keyword.keyword_miner.data.dto.KeywordSaveModel


import com.keyword.keyword_miner.databinding.RepositoryItemBinding

class TRepositoryViewHolder(
    private val binding:RepositoryItemBinding
):RecyclerView.ViewHolder(binding.root) {
    fun bind(item:KeywordSaveModel){
        binding.data = item
    }
}
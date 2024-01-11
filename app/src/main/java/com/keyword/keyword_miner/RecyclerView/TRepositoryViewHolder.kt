package com.keyword.keyword_miner.RecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.databinding.RepositoryItemBinding
import com.keyword.keyword_miner.ui.activity.RepositoryActivity

class TRepositoryViewHolder(
    private val binding:RepositoryItemBinding,
    private val handler:RepositoryActivity.RepositoryHandler
):RecyclerView.ViewHolder(binding.root) {
    fun bind(item:KeywordSaveModel){
        binding.data = item
        binding.handler=handler
    }
}
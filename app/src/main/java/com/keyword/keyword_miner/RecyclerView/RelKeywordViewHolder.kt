package com.keyword.keyword_miner.RecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.keyword.keyword_miner.databinding.RelkeywordItemBinding
import com.keyword.keyword_miner.domain.model.relkeyworddata.RelKeywordDataModel
import com.keyword.keyword_miner.ui.activity.KeywordActivity
import com.keyword.keyword_miner.ui.fragments.RelFragment

class RelKeywordViewHolder(
    val binding: RelkeywordItemBinding,
    val handler: RelFragment.RelKeywordHandler
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RelKeywordDataModel) {
        binding.reldata=item
        binding.handler=handler
    }
}
package com.keyword.keyword_miner.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.databinding.RepositoryItemBinding

class TRepositoryRecyclerViewAdapter : ListAdapter<KeywordSaveModel, TRepositoryViewHolder>(
    diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<KeywordSaveModel>() {
            override fun areItemsTheSame(
                oldItem: KeywordSaveModel,
                newItem: KeywordSaveModel
            ): Boolean {
                return oldItem.keyword == newItem.keyword
            }

            override fun areContentsTheSame(
                oldItem: KeywordSaveModel,
                newItem: KeywordSaveModel
            ): Boolean {
                return oldItem==newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TRepositoryViewHolder {
        return TRepositoryViewHolder(
            RepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TRepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
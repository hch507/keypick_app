package com.example.keyword_miner.RecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.keyword_miner.Repository.RepositoryItem
import com.example.keyword_miner.databinding.ActivityRepositoryViewBinding
import java.text.SimpleDateFormat

class RepositoryViewHolder(val binding : ActivityRepositoryViewBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RepositoryItem){
        binding.keyword.text=item.keyword
        binding.monthCnt.text= item.monthCnt.toString()
        binding.blogCnt.text=item.blogCnt
        val sdf= SimpleDateFormat("yyyy/mm/dd")
        binding.datetime.text=sdf.format(item.datetime)
    }
}
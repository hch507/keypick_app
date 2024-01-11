package com.keyword.keyword_miner.RecyclerView
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.keyword.keyword_miner.data.dto.KeywordSaveModel
//import com.keyword.keyword_miner.databinding.ActivityRepositoryViewBinding
//import com.keyword.keyword_miner.ui.viewmodels.StorageViewModel
//import com.keyword.keyword_miner.utils.constant
//
//class RepositoryRecyclerViewAdapter(var viewModel: StorageViewModel) :
//    RecyclerView.Adapter<RepositoryViewHolder>() {
//    private var RepositoryList = listOf<KeywordSaveModel>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
//        Log.d(constant.TAG, "Adapter-onCreateViewHolder() called")
//        val itemhodler = RepositoryViewHolder(
//            ActivityRepositoryViewBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//        return itemhodler
//    }
//
//    override fun getItemCount(): Int {
//        return this.RepositoryList.size
//    }
//
//    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
//        Log.d(constant.TAG, "Adapter-onBindViewHolder() called")
//        holder.bind(this.RepositoryList[position])
//        holder.btn.setOnClickListener {
//
//            viewModel.requestDeleteData(this.RepositoryList[position])
//        }
//    }
//
//    fun submit(keywordSaveModel: List<KeywordSaveModel>) {
//
//        this.RepositoryList = keywordSaveModel
//    }
//}
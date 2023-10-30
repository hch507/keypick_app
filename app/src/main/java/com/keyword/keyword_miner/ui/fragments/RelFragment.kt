package com.keyword.keyword_miner.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyword.keyword_miner.KeywordInfo
import com.keyword.keyword_miner.RecyclerView.RelKeywordRecyclerViewAdapter
import com.keyword.keyword_miner.databinding.FragmentRelBinding
import com.keyword.keyword_miner.ui.viewmodels.KeywordViewModel
import com.keyword.keyword_miner.utils.constant

class RelFragment : Fragment() {

    var keywordList = ArrayList<KeywordInfo>()
    lateinit var binding:FragmentRelBinding

    val keywordViewModel by activityViewModels<KeywordViewModel>()
    lateinit var keywordAdapter: RelKeywordRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRelBinding.inflate(layoutInflater)

        keywordViewModel.currentRelData.observe(viewLifecycleOwner, Observer{KeywordInfoList->
            Log.d(constant.TAG, "KeywordFragment - onCreateView() - called${KeywordInfoList}")
            this.keywordList=KeywordInfoList

            keywordAdapter=RelKeywordRecyclerViewAdapter(keywordViewModel)
            keywordAdapter.submit(keywordList)

            binding.recyclerview.layoutManager = LinearLayoutManager(this.activity,
                LinearLayoutManager.VERTICAL, false)
            binding.recyclerview.adapter = keywordAdapter
        })
        return binding.root
    }

}
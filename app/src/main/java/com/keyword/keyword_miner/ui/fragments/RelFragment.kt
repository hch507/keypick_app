package com.keyword.keyword_miner.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyword.keyword_miner.KeywordInfo
import com.keyword.keyword_miner.RecyclerView.RelKeywordRecyclerViewAdapter
import com.keyword.keyword_miner.databinding.FragmentRelBinding
import com.keyword.keyword_miner.domain.Model.relKeywordData.RelKeywordDataModel
import com.keyword.keyword_miner.ui.viewmodels.KeywordViewModel
import com.keyword.keyword_miner.ui.viewmodels.keywordViewmodelTest
import com.keyword.keyword_miner.utils.MainUiState
import com.keyword.keyword_miner.utils.constant
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RelFragment : Fragment() {

    var keywordList = ArrayList<KeywordInfo>()
    lateinit var binding:FragmentRelBinding
    lateinit var keywordTestList : List<RelKeywordDataModel>
    val keywordViewModel by activityViewModels<KeywordViewModel>()
    val keywordViewmodelTest by activityViewModels<keywordViewmodelTest>()
    lateinit var keywordAdapter: RelKeywordRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRelBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                keywordViewmodelTest.currentRelData.collectLatest {

                    when(it){
                        is MainUiState.success ->{
                            Log.d("hhhh", "RelFragment - onCreateView() - called ${it.data}")
                            keywordTestList=it.data
                            keywordAdapter=RelKeywordRecyclerViewAdapter(keywordViewmodelTest)
                            keywordAdapter.submit(keywordTestList)

                            binding.recyclerview.apply {
                                layoutManager = LinearLayoutManager(context,
                                    LinearLayoutManager.VERTICAL, false)
                                    adapter = keywordAdapter
                            }
                        }
                        else ->{
                            Toast.makeText(getActivity(), "서버와 통신이 실패하였습니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
//        keywordViewModel.currentRelData.observe(viewLifecycleOwner, Observer{KeywordInfoList->
//            Log.d("HHHH", "KeywordFragment - onCreateView() - called${KeywordInfoList}")
//            this.keywordList=KeywordInfoList
//
//            keywordAdapter=RelKeywordRecyclerViewAdapter(keywordViewModel)
//            keywordAdapter.submit(keywordList)
//
//            binding.recyclerview.layoutManager = LinearLayoutManager(this.activity,
//                LinearLayoutManager.VERTICAL, false)
//            binding.recyclerview.adapter = keywordAdapter
//        })
        return binding.root
    }

}
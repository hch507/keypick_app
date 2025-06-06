package com.keyword.keyword_miner.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.keyword.keyword_miner.databinding.FragmentRankBinding
import com.keyword.keyword_miner.ui.viewmodels.RankViewmodel
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RankFragment : Fragment() {
    lateinit var binding : FragmentRankBinding
    lateinit var userEmail : String
    lateinit var searchTerm : String
    val rankViewmodel : RankViewmodel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRankBinding.inflate(layoutInflater)
        userEmail=rankViewmodel.getUserEmail()
        binding.nameTextView.text="${userEmail} 님의 블로그"

        binding.searchButton.setOnClickListener {
            if(binding.KeywordTextInputEditText.text!!.isEmpty()){
                binding.keywordInputLayout.error ="키워드를 입력해주세요."
                Toast.makeText(getActivity(),"키워드를 입력해주세요.",Toast.LENGTH_SHORT).show();
            }else {
                binding.keywordInputLayout.apply {
                    error =null
                    isErrorEnabled=false
                }
                searchTerm = binding.KeywordTextInputEditText.text.toString()
                rankViewmodel.getBlogRank(searchTerm)
                lifecycleScope.launch{
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                        rankViewmodel.currentRank.collectLatest {
                            when(it){
                                is MainUiState.success ->{
                                    Log.d("hhh", "onCreateView: ${it.data} ")
                                    var index = getIndexWithKeyword(it.data.blogLink, userEmail!!)
                                    if (index != null) {
                                        binding.rankingTextView.text="${userEmail}님의 ${searchTerm} 키워드 관련 포스팅 글은 상위 랭크 ${index+1}번째에 위치해있습니다."
                                    } else {
                                        binding.rankingTextView.text="아쉽게 100위안에 들지 못했습니다ㅠㅠ"
                                    }
                                }
                                is MainUiState.Error -> {

                                }
                                is MainUiState.Loading -> {

                                }
                            }

                        }

                    }
                }

            }

        }
        return binding.root
    }
    fun getIndexWithKeyword(list: List<String>, keyword: String): Int? {
        Log.d("hch", "getIndexWithKeyword:${list} ")
        return list.indexOfFirst { it.contains(keyword) }.takeIf { it != -1 }
    }


}
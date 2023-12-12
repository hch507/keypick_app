package com.keyword.keyword_miner.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.keyword.keyword_miner.data.Retrofit.RetrofitManager
import com.keyword.keyword_miner.ui.viewmodels.UserBlogViewmodel
import com.keyword.keyword_miner.databinding.FragmentRankBinding
import com.keyword.keyword_miner.utils.Blog_API
import com.keyword.keyword_miner.utils.RESPONSE_STATE
import com.keyword.keyword_miner.utils.constant


class RankFragment : Fragment() {
    lateinit var binding : FragmentRankBinding
    var blogname : String?=""
    var titleKeyword : String?=""
    val userBlgoViewModel by activityViewModels<UserBlogViewmodel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRankBinding.inflate(layoutInflater)
        userBlgoViewModel.currentBlogData.observe(viewLifecycleOwner, Observer { userdata->
            this.blogname = userdata
            binding.name.text="${userdata} 님의 블로그"
        })
        binding.search.setOnClickListener {
            Log.d("HHH", "RankFragment - onCreateView() - called${binding.name.text}")
            if(binding.postingKeyword.text!!.isEmpty()){
                binding.inputKeyword.error ="키워드를 입력해주세요."
                Toast.makeText(getActivity(),"키워드를 입력해주세요.",Toast.LENGTH_SHORT).show();
            }else {
                binding.inputKeyword.error = null
                binding.inputKeyword.isErrorEnabled = false
                titleKeyword = binding.postingKeyword.text.toString()

                RetrofitManager.instance.searchBlogCnt(searchTerm = titleKeyword, sort = Blog_API.SORT2) { responseState, responseData ->
                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            if (responseData != null) {
                                Log.d("HCH", "api 호출에 성공하였습니다 ${responseData}")
                            }
                            val index = responseData?.get(0)
                                ?.let { it1 -> getIndexWithKeyword(it1.blogname, blogname!!) }
                            if (index != null) {
                                binding.ranking.text="${blogname}님의 ${titleKeyword} 키워드 관련 포스팅 글은 상위 랭크 ${index+1}번째에 위치해있습니다."
                            } else {
                                binding.ranking.text="아쉽게 100위안에 들지 못했습니다ㅠㅠ"
                            }

                        }
                        RESPONSE_STATE.FAIL -> {

                            Log.d(constant.TAG, "api 호충에 실패 하였습니다")


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
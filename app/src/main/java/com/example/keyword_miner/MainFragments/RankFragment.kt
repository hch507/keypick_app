package com.example.keyword_miner.MainFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.keyword_miner.R
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.databinding.FragmentRankBinding
import com.example.keyword_miner.utils.Blog_API
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant


class RankFragment : Fragment() {
    lateinit var binding : FragmentRankBinding
    var blogname : String?=""
    var titleKeyword : String?=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRankBinding.inflate(layoutInflater)

        binding.search.setOnClickListener {
            Log.d("HHH", "RankFragment - onCreateView() - called${binding.blogName.text}")
            if(binding.blogName.text.isEmpty()||binding.postingKeyword.text.isEmpty()){
                Log.d("HHH", "RankFragment - onCreateView() - called!!!!!")
                Toast.makeText(getActivity(),"Toast Message",Toast.LENGTH_SHORT).show();
            }else {
                blogname = binding.blogName.text.toString()
                titleKeyword = binding.postingKeyword.text.toString()

                RetrofitManager.instance.searchBlogCnt(searchTerm = titleKeyword, sort = Blog_API.SORT2) { responseState, responseData ->
                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            if (responseData != null) {
                                Log.d("HCH", "api 호출에 성공하였습니다 ${responseData.get(0).blogname}")
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
        return list.indexOfFirst { it.contains(keyword) }.takeIf { it != -1 }
    }


}
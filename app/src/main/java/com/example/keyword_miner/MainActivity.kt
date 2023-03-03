package com.example.keyword_miner

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.keyword_miner.KeywordSearch.KeywordActivity
import com.example.keyword_miner.KeywordSearch.KeywordViewModel
import com.example.keyword_miner.databinding.ActivityMainBinding
import com.example.keyword_miner.utils.constant.TAG

class MainActivity : AppCompatActivity() {
    private var mbinding: ActivityMainBinding? = null
    private val binding get() = mbinding!!

    lateinit var keywordViewModel: KeywordViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        keywordViewModel= ViewModelProvider(this).get(KeywordViewModel::class.java)

        binding.findButton.setOnClickListener {

            Log.d(TAG, "MainActivity - 검색 버튼이 클릭되었다.")
            val intent = Intent(this, KeywordActivity::class.java)

//            val bundle = Bundle()
            var userSearchInput = binding.keywordName.text.toString().replace(" ", "")
            userSearchInput=convertToUpperCase(userSearchInput)
            Log.d("HCH", "MainActivity - onCreate() - called ${userSearchInput}")
            intent.putExtra("searchterm", userSearchInput)

//            //연관 검색 api 호출
//            RetrofitManager.instance.searchKeywordRel(searchTerm = userSearchInput) { responseState, responseArrayList ->
//                when (responseState) {
//                    RESPONSE_STATE.OKAY -> {
//                        Log.d(TAG, "api 호출에 성공하였습니다 ${responseArrayList?.get(0)}")
//                        //Arraylist 전달하기
//                        //bundle에 넣어서 전달하기
//                        bundle.putSerializable("array_list", responseArrayList)
//                        intent.putExtra("bundle_array", bundle)
//
//   //                     startActivity(intent)
//                    }
//                    RESPONSE_STATE.FAIL -> {
//                        Toast.makeText(this, "api 호출 오류 입니다", Toast.LENGTH_SHORT).show()
//                        Log.d(TAG, "api 호충에 실패 하였습니다")
//                    }
//                }
//            }
//
//            //검색어 그래프
//            RetrofitManager.instance.searcKData(searchTerm = userSearchInput) { responseState, responseData ->
//                when (responseState) {
//                    RESPONSE_STATE.OKAY -> {
//                        Log.d(constant.TAG, "api 호출에 성공하였습니다 ${responseData}")
//                        //Arraylist 전달하기
//                        //bundle에 넣어서 전달하기
//
//                        bundle.putSerializable("data_array_list", responseData as Serializable?)
//                        intent.putExtra("bundle_array_data", bundle)
//
//
//                    }
//                    RESPONSE_STATE.FAIL -> {
//                        Toast.makeText(this, "api 호출 오류 입니다", Toast.LENGTH_SHORT).show()
//                        Log.d(constant.TAG, "api 호충에 실패 하였습니다")
//                    }
//                }
//            }
//
//            RetrofitManager.instance.searchBlogCnt(searchTerm = userSearchInput) { responseState, responseData ->
//                when (responseState) {
//                    RESPONSE_STATE.OKAY -> {
//                        Log.d(constant.TAG, "api 호출에 성공하였습니다 ${responseData}")
//                        Log.d("LHH", "api 호출에 성공하였습니다 ${responseData}")
//
//                        //Arraylist 전달하기
//                        //bundle에 넣어서 전달하기
//
//                        val temp = responseData as Serializable
//                        bundle.putSerializable("blogcnt_array_list",temp)
//                        intent.putExtra("bundle_array_blogcnt", bundle)
//
//
//                    }
//                    RESPONSE_STATE.FAIL -> {
//                        Toast.makeText(this, "api 호출 오류 입니다", Toast.LENGTH_SHORT).show()
//                        Log.d(constant.TAG, "api 호충에 실패 하였습니다")
//                        Log.d("LHH", "api 호충에 실패 하였습니다")
//
//                    }
//
//                }
//
//            }

//            Handler().postDelayed({
//                startActivity(intent)
//            }, 2000)
            startActivity(intent)
        }

    }
    fun convertToUpperCase(input: String): String {
        return if (input.matches("[a-zA-Z]+".toRegex()) && input.contains("[a-z]".toRegex())) {
            input.toUpperCase()
        } else {
            input
        }
    }

}
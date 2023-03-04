package com.example.keyword_miner


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import com.example.keyword_miner.KeywordSearch.*
import com.example.keyword_miner.MainFragments.RankFragment
import com.example.keyword_miner.MainFragments.UserFragment
import com.example.keyword_miner.databinding.ActivityMainBinding

import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private var mbinding: ActivityMainBinding? = null
    private val binding get() = mbinding!!


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, KeywordActivity::class.java)
        val list = listOf(UserFragment(), RankFragment())
        //어답터 생성
        val pageAdapter = FragmentPageAdapter(list, this)
        //어답터와 뷰페이저 연결
        binding.ViewPage.adapter = pageAdapter
        //탭 레이아웃 타이틀 설정
        val titles = listOf("검색어", "연관검색어")
        //탭 레이아웃과 뷰페이저 연걸
        TabLayoutMediator(binding.tabLayout, binding.ViewPage) { tab, position ->
            tab.text = titles.get(position)
        }.attach()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var userSearchInput = query?.replace(" ", "")

 //               userSearchInput = userSearchInput?.let { convertToUpperCase(it) }
                Log.d("HHH", "MainActivity - onQueryTextSubmit() - called${userSearchInput}")
                if (userSearchInput != null) {
                    intent.putExtra("searchterm", userSearchInput)
                }
                startActivity(intent)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // 검색창에서 글자가 변경이 일어날 때마다 호출

                return true
            }
        })
    }

}
























//        keywordViewModel= ViewModelProvider(this).get(KeywordViewModel::class.java)
//
//        binding.findButton.setOnClickListener {
//
//            Log.d(TAG, "MainActivity - 검색 버튼이 클릭되었다.")
//            val intent = Intent(this, KeywordActivity::class.java)
//
////            val bundle = Bundle()
//            var userSearchInput = binding.keywordName.text.toString().replace(" ", "")
//            userSearchInput=convertToUpperCase(userSearchInput)
//            Log.d("HCH", "MainActivity - onCreate() - called ${userSearchInput}")
//            intent.putExtra("searchterm", userSearchInput)

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







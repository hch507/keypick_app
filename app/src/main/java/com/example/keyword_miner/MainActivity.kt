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
import com.example.keyword_miner.Model.UserBlog
import com.example.keyword_miner.Model.blogData
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.databinding.ActivityMainBinding
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant

import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private var mbinding: ActivityMainBinding? = null
    private val binding get() = mbinding!!

    var userdata =ArrayList<UserBlog>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val list = listOf(UserFragment(), RankFragment())
        //어답터 생성
        val pageAdapter = FragmentPageAdapter(list, this)
        //어답터와 뷰페이저 연결
        binding.ViewPage.adapter = pageAdapter
        //탭 레이아웃 타이틀 설정
        val titles = listOf("Home", "Ranking")
        //탭 레이아웃과 뷰페이저 연걸
        TabLayoutMediator(binding.tabLayout, binding.ViewPage) { tab, position ->
            tab.text = titles.get(position)
        }.attach()

        val bundle = intent.getBundleExtra("Bundle_Array_List")
        if (bundle != null) {
            userdata= bundle.getSerializable("Array_List") as ArrayList<UserBlog>
            Log.d("HHH", "MainActivity - onCreate() - called${userdata.get(0).email}")

        }
        val username = userdata.get(0).email?.substring(0, userdata.get(0).email!!.indexOf('@'))

//        if (username != null) {
//            RetrofitManager.instance.blogData(email = username) { responseState, responseArrayList ->
//                when (responseState) {
//                    RESPONSE_STATE.OKAY -> {
//                        Log.d("HHH", "api 호출에 성공하였습니다 ${responseArrayList?.get(0)}")
//
//                    }
//                    RESPONSE_STATE.FAIL -> {
//
//                        Log.d(constant.TAG, "api 호충에 실패 하였습니다")
//                    }
//                }
//            }
//        }

        val intent = Intent(this, KeywordActivity::class.java)

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




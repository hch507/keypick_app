package com.example.keyword_miner


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import com.example.keyword_miner.KeywordSearch.*
import com.example.keyword_miner.MainFragments.RankFragment
import com.example.keyword_miner.MainFragments.UserFragment
import com.example.keyword_miner.Model.UserBlog
import com.example.keyword_miner.Model.blogData
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.User.UserBlogViewmodel
import com.example.keyword_miner.databinding.ActivityMainBinding
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant

import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private var mbinding: ActivityMainBinding? = null
    private val binding get() = mbinding!!
    val userBlogViewModel: UserBlogViewmodel by viewModels()

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
        val userEmail = userdata.get(0).email?.substring(0, userdata.get(0).email!!.indexOf('@'))
        val username =userdata.get(0).name

        userBlogViewModel.UserSet(userEmail!!,username!!)
        userBlogViewModel.BlogCntUpdate(userEmail)

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
//val xmlString = "<visitorcnts><visitorcnt id=\"20230305\" cnt=\"0\" /><visitorcnt id=\"20230306\" cnt=\"0\" /><visitorcnt id=\"20230307\" cnt=\"0\" /><visitorcnt id=\"20230308\" cnt=\"0\" /><visitorcnt id=\"20230309\" cnt=\"0\" /></visitorcnts>"
//
//val doc = Jsoup.parse(xmlString, "", Parser.xmlParser())
//val visitorCnts = doc.getElementsByTag("visitorcnt")
//
//val idList = mutableListOf<String>()
//val cntList = mutableListOf<String>()
//
//for (visitorCnt in visitorCnts) {
//    val id = visitorCnt.attr("id")
//    val cnt = visitorCnt.attr("cnt")
//    idList.add(id)
//    cntList.add(cnt)
//}
//
//println("id list: $idList")
//println("cnt list: $cntList")



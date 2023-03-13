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
import com.example.keyword_miner.Repository.RepositoryActivity
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.User.LoginActivity
import com.example.keyword_miner.User.UserBlogViewmodel
import com.example.keyword_miner.databinding.ActivityMainBinding
import com.example.keyword_miner.sharePref.App
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant

import com.google.android.material.tabs.TabLayoutMediator
import com.navercorp.nid.NaverIdLoginSDK

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding

    val userBlogViewModel: UserBlogViewmodel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchViewMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@MainActivity, KeywordActivity::class.java)
                val userSearchInput = query?.replace(" ", "")
                if (userSearchInput != null) {
                    intent.putExtra("searchterm", userSearchInput)
                }
                startActivity(intent)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
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

//        val bundle = intent.getBundleExtra("Bundle_Array_List")
//        if (bundle != null) {
//            userdata= bundle.getSerializable("Array_List") as ArrayList<UserBlog>
//            Log.d("HHH", "MainActivity - onCreate() - called${userdata.get(0).email}")

//        }
      //  val userEmail = userdata.get(0).email?.substring(0, userdata.get(0).email!!.indexOf('@'))
      //  val username =userdata.get(0).name

        val userEmail = App.prefs.getEmail("userEmail","").substring(0, App.prefs.getEmail("userEmail","").indexOf('@'))
        Log.d("AAA", "MainActivity - onCreate() - called ${userEmail}")
        val username =App.prefs.getName("userName","")
        Log.d("HHH", "MainActivity - onCreate() - called ${username}")
          userBlogViewModel.UserSet(userEmail!!,username!!)
          userBlogViewModel.BlogCntUpdate(userEmail)
//        intent = Intent(this, KeywordActivity::class.java)
//        binding.searchViewMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        binding.repository.setOnClickListener {
            intent = Intent(this@MainActivity, RepositoryActivity::class.java)
            startActivity(intent)
        }
        binding.userBtn.setOnClickListener{
            App.prefs.setboolean("isLoggedIn",false)
            intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

 //       })
}




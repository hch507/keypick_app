package com.example.keyword_miner


import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.example.keyword_miner.KeywordSearch.*
import com.example.keyword_miner.MainFragments.RankFragment
import com.example.keyword_miner.MainFragments.UserFragment
import com.example.keyword_miner.User.UserBlogViewmodel
import com.example.keyword_miner.databinding.ActivityMainBinding
import com.example.keyword_miner.sharePref.App

import com.google.android.material.tabs.TabLayoutMediator

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
                binding.searchViewMain.setQuery("",false)
                binding.searchViewMain.clearFocus()
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


//        val userEmail = App.prefs.getEmail("userEmail","").substring(0, App.prefs.getEmail("userEmail","").indexOf('@'))
        val userEmail = App.prefs.getEmail("userEmail","")

        Log.d("AAA", "MainActivity - onCreate() - called ${userEmail}")

        userBlogViewModel.UserSet(userEmail!!)
        userBlogViewModel.BlogCntUpdate(userEmail)

//        val username =App.prefs.getName("userName","")
//        Log.d("HHH", "MainActivity - onCreate() - called ${username}")

//        intent = Intent(this, KeywordActivity::class.java)
//        binding.searchViewMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        binding.userBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)

            builder.setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까??")
                .setPositiveButton("확인",DialogInterface.OnClickListener{dialog, id ->
                    App.prefs.setboolean("isLoggedIn",false)
                    intent = Intent(this@MainActivity, BlogIdActivity::class.java)
                    startActivity(intent)
                    finish()

                })
                .setNegativeButton("취소",DialogInterface.OnClickListener{dialog, id ->
                    Toast.makeText(this@MainActivity, "취소", Toast.LENGTH_SHORT).show()

                })
            builder.show()
//            App.prefs.setboolean("isLoggedIn",false)
//            intent = Intent(this@MainActivity, BlogIdActivity::class.java)
//            startActivity(intent)
//            finish()
        }
    }

 //       })
}




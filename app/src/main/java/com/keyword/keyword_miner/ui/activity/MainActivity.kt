package com.keyword.keyword_miner.ui.activity


import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.keyword.keyword_miner.KeywordSearch.*
import com.keyword.keyword_miner.ui.fragments.RankFragment
import com.keyword.keyword_miner.ui.fragments.UserFragment
import com.keyword.keyword_miner.databinding.ActivityMainBinding
import com.keyword.keyword_miner.ui.App
import com.google.android.material.tabs.TabLayoutMediator
import com.keyword.keyword_miner.ui.viewmodels.UserBlogIdViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //search해서 넘기기
        binding.searchViewMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@MainActivity, KeywordActivity::class.java)
                val userSearchInput = query?.replace(" ", "")
                if (userSearchInput != null) {
                    intent.putExtra("searchterm", userSearchInput)
                }
                binding.searchViewMain.setQuery("", false)
                binding.searchViewMain.clearFocus()
                startActivity(intent)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        val list = listOf(UserFragment(), RankFragment())
        val pageAdapter = FragmentPageAdapter(list, this)
        binding.ViewPage.adapter = pageAdapter
        val titles = listOf("Home", "Ranking")
        TabLayoutMediator(binding.tabLayout, binding.ViewPage) { tab, position ->
            tab.text = titles.get(position)
        }.attach()

        binding.userBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까??")
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                    App.prefs.setboolean("isLoggedIn", false)
                    intent = Intent(this@MainActivity, BlogIdActivity::class.java)
                    startActivity(intent)
                    finish()

                })
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(this@MainActivity, "취소", Toast.LENGTH_SHORT).show()

                })
            builder.show()

        }
    }
}




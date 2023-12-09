package com.keyword.keyword_miner.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.keyword.keyword_miner.data.Retrofit.RetrofitManager
import com.keyword.keyword_miner.databinding.ActivityBlogIdBinding
import com.keyword.keyword_miner.ui.App
import com.keyword.keyword_miner.ui.viewmodels.LoginViewModel
import com.keyword.keyword_miner.utils.MainUiState
import com.keyword.keyword_miner.utils.RESPONSE_STATE
import com.keyword.keyword_miner.utils.constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BlogIdActivity : AppCompatActivity() {
    lateinit var binding: ActivityBlogIdBinding
    lateinit var userEmail: String
    val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBlogIdBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        loginViewModel.requestLogin("dafdasgfzcvd")
        if (App.prefs.getboolean("isLoggedIn", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // LoginActivity를 종료한다
            return // 이후의 코드는 실행하지 않는다
        }

        binding.registerBtn.setOnClickListener {
            userEmail = binding.blogID.text.toString()
            if (userEmail != null) {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        loginViewModel.requestLogin(userEmail)
                        loginViewModel.currentBlogCnt.collectLatest {
                            when (it) {
                                is MainUiState.success -> {
                                    Log.d("LoginState", "onCreate: success ")
                                    binding.blogID.error = null
                                    val intent =
                                        Intent(this@BlogIdActivity, MainActivity::class.java)
                                    App.prefs.setboolean("isLoggedIn", true)
                                    Log.d("HHH", "LoginActivity - onSuccess() - called${userEmail}")
                                    App.prefs.setEmail("userEmail", userEmail)
                                    startActivity(intent)
                                    finish() // LoginActivity를 종료한다

                                }

                                is MainUiState.Error -> {
                                    Log.d("LoginState", "onCreate: fail ")
                                    binding.inputBlog.error = "블로그아이디가 존재하지않습니다"
                                    Toast.makeText(
                                        this@BlogIdActivity,
                                        "블로그 아이디를 다시 확인해주세요",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                is MainUiState.Loading -> {}
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "블로그 아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        binding.nonBtn.setOnClickListener {
            val intent = Intent(this, NonLoginactivity::class.java)
            startActivity(intent)
            finish() // LoginActivity를 종료한다
        }
    }

}
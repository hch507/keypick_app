package com.example.keyword_miner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.keyword_miner.Model.MyBlogData
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.databinding.ActivityBlogIdBinding
import com.example.keyword_miner.sharePref.App
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant
import org.jsoup.Jsoup
import org.jsoup.parser.Parser


class BlogIdActivity : AppCompatActivity() {
    lateinit var binding : ActivityBlogIdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityBlogIdBinding.inflate(layoutInflater)
        var userEmail :String =""
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        if (App.prefs.getboolean("isLoggedIn",false)) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish() // LoginActivity를 종료한다
//            return // 이후의 코드는 실행하지 않는다
//        }
        binding.registerBtn.setOnClickListener {
            userEmail=binding.blogID.text.toString()

            if (userEmail != null) {
                RetrofitManager.instance.blogData(email = userEmail) { responseState, responsebody ->
                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            if(responsebody==null){
                                binding.inputBlog.error ="블로그아이디가 존재하지않습니다"
                                Toast.makeText(this, "블로그 아이디를 다시 확인해주세요", Toast.LENGTH_SHORT).show()
                            }else {
                                binding.blogID.error = null
                                val intent = Intent(this, MainActivity::class.java)
                                App.prefs.setboolean("isLoggedIn", true)
                                Log.d("HHH", "LoginActivity - onSuccess() - called${userEmail}")
                                App.prefs.setEmail("userEmail", userEmail)
                                startActivity(intent)
                                finish() // LoginActivity를 종료한다
                            }
                        }
                        RESPONSE_STATE.FAIL -> {
                            Log.d(constant.TAG, "api 호충에 실패 하였습니다")
                        }
                    }
                }
            }else{
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
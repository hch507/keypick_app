package com.example.blog_cnt_api

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.blog_cnt_api.databinding.ActivityMainBinding
import com.example.unsplash_app_tutorial.retrofit.RetrofitManager
import com.example.unsplash_app_tutorial.utils.Constant
import com.example.unsplash_app_tutorial.utils.RESPONSE_STATE
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private var mbinding  : ActivityMainBinding? = null
    private val binding get() = mbinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findButton.setOnClickListener {

            Log.d(Constant.TAG, "MainActivity - 검색 버튼이 클릭되었다.")

            val userSearchInput = binding.keywordName.text.toString()
            RetrofitManager.instance.searchPhotos(searchTerm = userSearchInput) { responseState, responseData->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        Log.d(Constant.TAG, "api 호출에 성공하였습니다 ${responseData}")
                        //Arraylist 전달하기
                        //bundle에 넣어서 전달하기
//                        val intent = Intent(this,ItemActivity::class.java)

//                        val bundle = Bundle()
//                        bundle.putSerializable("array_list", responseData as Serializable?)
//                        intent.putExtra("bundle_array",bundle)
//
//                        startActivity(intent)

                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(this, "api 호출 오류 입니다", Toast.LENGTH_SHORT).show()
                        Log.d(Constant.TAG, "api 호충에 실패 하였습니다")
                    }
                }
            }
        }
    }

}
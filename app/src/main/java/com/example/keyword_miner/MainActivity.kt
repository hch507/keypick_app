package com.example.keyword_miner

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.databinding.ActivityMainBinding
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant
import com.example.keyword_miner.utils.constant.TAG
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private var mbinding  : ActivityMainBinding? = null
    private val binding get() = mbinding!!
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.findButton.setOnClickListener {

            Log.d(TAG, "MainActivity - 검색 버튼이 클릭되었다.")
            val intent = Intent(this,KeywordActivity::class.java )
            val userSearchInput = binding.keywordName.text.toString().replace(" ", "")

            RetrofitManager.instance.searchKeywordRel(searchTerm = userSearchInput) { responseState, responseArrayList ->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        Log.d(TAG, "api 호출에 성공하였습니다 ${responseArrayList?.get(0)}")
                        //Arraylist 전달하기
                        //bundle에 넣어서 전달하기
                        val bundle = Bundle()
                        bundle.putSerializable("array_list",responseArrayList)
                        intent.putExtra("bundle_array",bundle)

                        startActivity(intent)
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(this, "api 호출 오류 입니다", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "api 호충에 실패 하였습니다")
                    }
                }
            }

            RetrofitManager.instance.searcKData(searchTerm = userSearchInput){responseState, responseData->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        Log.d(constant.TAG, "api 호출에 성공하였습니다 ${responseData}")
                        //Arraylist 전달하기
                        //bundle에 넣어서 전달하기


                        val bundle = Bundle()
                        bundle.putSerializable("data_array_list", responseData as Serializable?)
                        intent.putExtra("bundle_array_data",bundle)

                        startActivity(intent)


                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(this, "api 호출 오류 입니다", Toast.LENGTH_SHORT).show()
                        Log.d(constant.TAG, "api 호충에 실패 하였습니다")
                    }
                }}

        }
    }
}
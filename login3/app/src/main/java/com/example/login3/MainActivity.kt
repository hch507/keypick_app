package com.example.login3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.utils.constant.TAG
import com.example.login3.databinding.ActivityMainBinding
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.NaverIdLoginSDK.oauthLoginCallback
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.oauth.view.NidOAuthLoginButton.Companion.launcher


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NaverIdLoginSDK.initialize(this, "CYg05Le49trbKuuQtJmj", "MjzgIUOP2F", "keyword_miner")
        oauthLoginCallback?.let { binding.buttonOAuthLoginImg.setOAuthLogin(it) }


        binding.buttonOAuthLoginImg.setOnClickListener{
            val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
//                binding.name.text = NaverIdLoginSDK.getAccessToken()
//                binding.id.text = NaverIdLoginSDK.getRefreshToken()
//                binding.gender.text = NaverIdLoginSDK.getExpiresAt().toString()
                var token = NaverIdLoginSDK.getAccessToken().toString()
                var header = "Bearer $token"
                RetrofitManager.instance.search(header=header, completion = {
                        responseState, responseArrayList ->
                    Log.d(TAG, "MainActivity - onCreate() - called ${responseState}")
//                when(responseState){
//                    RESPONSESTATE.OkAY->{
////                    Log.d(TAG, "MainActivity-onCreate() called,RESPONSESTATE.OkAY ${responseArrayList} ")
////
////                    val intent = Intent(this, Recycler_view::class.java)
////                    //arraylist를 bundle에 담아 보내기
////                    val bundle = Bundle()
////                    bundle.putSerializable("Array_List",responseArrayList)
////                    intent.putExtra("Bundle_Array_List", bundle)
////
////                    startActivity(intent)
//
//                    }
//                    RESPONSESTATE.FALSE->{
////                    Log.d(TAG, "MainActivity-onCreate() called, RESPONSESTATE.FALSE")
//                    }
//                }
//                when(responseState){
//
//                }


                })

            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@MainActivity,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

            NaverIdLoginSDK.initialize(this, "CYg05Le49trbKuuQtJmj", "MjzgIUOP2F", "keyword_miner")
            NaverIdLoginSDK.authenticate(this, oauthLoginCallback)

//            RetrofitManager.instance.search(header=header, completion = {
//                    responseState, responseArrayList ->
//                Log.d(TAG, "MainActivity - onCreate() - called ${responseState}")
////                when(responseState){
////                    RESPONSESTATE.OkAY->{
//////                    Log.d(TAG, "MainActivity-onCreate() called,RESPONSESTATE.OkAY ${responseArrayList} ")
//////
//////                    val intent = Intent(this, Recycler_view::class.java)
//////                    //arraylist를 bundle에 담아 보내기
//////                    val bundle = Bundle()
//////                    bundle.putSerializable("Array_List",responseArrayList)
//////                    intent.putExtra("Bundle_Array_List", bundle)
//////
//////                    startActivity(intent)
////
////                    }
////                    RESPONSESTATE.FALSE->{
//////                    Log.d(TAG, "MainActivity-onCreate() called, RESPONSESTATE.FALSE")
////                    }
////                }
////                when(responseState){
////
////                }
//
//
//            })
        }

    }


}
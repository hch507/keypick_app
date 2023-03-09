package com.example.keyword_miner.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.keyword_miner.MainActivity
import com.example.keyword_miner.Model.UserBlog
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.databinding.ActivityLoginBinding
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    private var token :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        var userdata =ArrayList<UserBlog>()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NaverIdLoginSDK.initialize(this, "CYg05Le49trbKuuQtJmj", "MjzgIUOP2F", "keyword_miner")
        binding.buttonOAuthLoginImg.setOnClickListener{
            startNaverLogin()
//            val oauthLoginCallback = object : OAuthLoginCallback {
//                override fun onSuccess() {
//                    token = NaverIdLoginSDK.getAccessToken().toString()
//
//                    NidOAuthLogin().callProfileApi(nidProfileCallback)
////                    var header = "Bearer $token"
////                    Log.d("HHH", "LoginActivity-onSuccess() called${header}")
////                    RetrofitManager.instance.userData(header = header, completion = { responseState, responseArrayList ->
////                            Log.d(constant.TAG, "MainActivity - onCreate() - called ${responseArrayList}")
////                            when(responseState) {
////                                RESPONSE_STATE.OKAY -> {
////                                if (responseArrayList != null) {
////                                    userdata=responseArrayList
////                                }
////                                val username = userdata.get(0).email?.substring(0, userdata.get(0).email!!.indexOf('@'))
////                                binding.email.text=username
////                                binding.name.text=userdata.get(0).name
//////                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//////                                    //arraylist를 bundle에 담아 보내기
//////                                    val bundle = Bundle()
//////                                    bundle.putSerializable("Array_List",responseArrayList)
//////                                    intent.putExtra("Bundle_Array_List", bundle)
//////
//////                                    startActivity(intent)
////                                }
////                                RESPONSE_STATE.FAIL->{
////                                    Log.d("HHH", "LoginActivity-onSuccess() called Fail")
////                                }
////                            }
////                        })
//                }
//
//
//                override fun onFailure(httpStatus: Int, message: String) {
//                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
//                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
//                    Toast.makeText(this@LoginActivity,"errorCode:$errorCode, errorDesc:$errorDescription",
//                        Toast.LENGTH_SHORT).show()
//                }
//                override fun onError(errorCode: Int, message: String) {
//                    onFailure(errorCode, message)
//                }
//            }
//            NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
//
//            var header = "Bearer $token"
//            Log.d("HHH", "LoginActivity-onSuccess() called${header}")
        }
    }

    private fun startNaverLogin() {
        var naverToken: String? = ""
        var userData = ArrayList<UserBlog>()
        Log.d("HHH", "LoginActivity-startNaverLogin() called")
        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userEmail = response.profile?.email
                val userName = response.profile?.name
                var userDataItem = UserBlog(userEmail,userName)
                userData.add(userDataItem)
                Log.d("HHH", "LoginActivity-onSuccess() called ${userEmail}")
                Log.d("HHH", "LoginActivity-onSuccess() called ${userName}")
                Toast.makeText(this@LoginActivity, "네이버 아이디 로그인 성공!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    //arraylist를 bundle에 담아 보내기
                val bundle = Bundle()
                bundle.putSerializable("Array_List",userData)
                intent.putExtra("Bundle_Array_List", bundle)

                startActivity(intent)
                //   binding.tvResult.text = "id: ${userId} \ntoken: ${naverToken}"

            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(
                    this@LoginActivity, "errorCode: ${errorCode}\n" +
                            "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()
                Log.d("HHH", "LoginActivity-onSuccess() called")

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@LoginActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

}
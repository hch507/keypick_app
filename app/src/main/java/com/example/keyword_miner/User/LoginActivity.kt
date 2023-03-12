package com.example.keyword_miner.User

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.keyword_miner.MainActivity
import com.example.keyword_miner.Model.UserBlog
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.databinding.ActivityLoginBinding
import com.example.keyword_miner.sharePref.App
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
        if (App.prefs.getboolean("isLoggedIn",false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // LoginActivity를 종료한다
            return // 이후의 코드는 실행하지 않는다
        }else{
            NaverIdLoginSDK.logout()
            Log.d("HHH", "LoginActivity - onCreate() - called 로그아웃")
        }

        binding.buttonOAuthLoginImg.setOnClickListener{
            startNaverLogin()

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
                Toast.makeText(this@LoginActivity, "네이버 아이디 로그인 성공!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    //arraylist를 bundle에 담아 보내기
//                val preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
//                preferences.edit().putBoolean("isLoggedIn", true).apply()
                App.prefs.setboolean("isLoggedIn",true)
                Log.d("HHH", "LoginActivity - onSuccess() - called${userEmail}")
                App.prefs.setEmail("userEmail",userEmail!!)
                App.prefs.setName("userName",userName!!)

                Log.d("HHH", "LoginActivity - onSuccess() - called${App.prefs.getEmail("userEmail","")}")
//                val bundle = Bundle()
//                bundle.putSerializable("Array_List",userData)
//                intent.putExtra("Bundle_Array_List", bundle)

                startActivity(intent)
                finish()
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
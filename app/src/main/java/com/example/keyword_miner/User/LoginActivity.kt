package com.example.keyword_miner.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.keyword_miner.MainActivity
import com.example.keyword_miner.Model.UserBlog
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.databinding.ActivityLoginBinding
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        var userdata =ArrayList<UserBlog>()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NaverIdLoginSDK.initialize(this, "CYg05Le49trbKuuQtJmj", "MjzgIUOP2F", "keyword_miner")
        binding.buttonOAuthLoginImg.setOnClickListener{
            val oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {

                    var token = NaverIdLoginSDK.getAccessToken().toString()
                    var header = "Bearer $token"
                    RetrofitManager.instance.userData(
                        header = header,
                        completion = { responseState, responseArrayList ->
                            Log.d(constant.TAG, "MainActivity - onCreate() - called ${responseArrayList}")
                            when(responseState) {
                                RESPONSE_STATE.OKAY -> {
                                if (responseArrayList != null) {
                                    userdata=responseArrayList
                                }
                                val username = userdata.get(0).email?.substring(0, userdata.get(0).email!!.indexOf('@'))
                                binding.email.text=username
                                binding.name.text=userdata.get(0).name
//                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                                    //arraylist를 bundle에 담아 보내기
//                                    val bundle = Bundle()
//                                    bundle.putSerializable("Array_List",responseArrayList)
//                                    intent.putExtra("Bundle_Array_List", bundle)
//
//                                    startActivity(intent)
                                }
                                RESPONSE_STATE.FAIL->{

                                }
                            }
                        })
                }


                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
//                    Toast.makeText(this,"errorCode:$errorCode, errorDesc:$errorDescription",
//                        Toast.LENGTH_SHORT).show()
                }
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }


            NaverIdLoginSDK.authenticate(this, oauthLoginCallback)


        }
    }
}
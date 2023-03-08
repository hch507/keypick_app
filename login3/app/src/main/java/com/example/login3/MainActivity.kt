package com.example.login3


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.api_project.Model.Item
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant.TAG
import com.example.login3.databinding.ActivityMainBinding
import com.navercorp.nid.NaverIdLoginSDK
import android.content.Intent
import com.navercorp.nid.NaverIdLoginSDK.oauthLoginCallback
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.oauth.view.NidOAuthLoginButton.Companion.launcher


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var itemlist =ArrayList<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NaverIdLoginSDK.initialize(this, "CYg05Le49trbKuuQtJmj", "MjzgIUOP2F", "keyword_miner")
 //       oauthLoginCallback?.let { binding.buttonOAuthLoginImg.setOAuthLogin(it) }


        binding.buttonOAuthLoginImg.setOnClickListener{
            val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {

                var token = NaverIdLoginSDK.getAccessToken().toString()
                var header = "Bearer $token"
                RetrofitManager.instance.search(
                    header = header,
                    completion = { responseState, responseArrayList ->
                        Log.d(TAG, "MainActivity - onCreate() - called ${responseArrayList}")
                        when(responseState) {
                            RESPONSE_STATE.OKAY -> {
                                if (responseArrayList != null) {
                                    itemlist=responseArrayList
                                }
                                val username = itemlist.get(0).email?.substring(0, itemlist.get(0).email!!.indexOf('@'))
                                binding.email.text=username
                                binding.name.text=itemlist.get(0).name
//                                val intent = Intent(this@MainActivity,UserActivity::class.java)
//                                //arraylist를 bundle에 담아 보내기
//                                val bundle = Bundle()
//                                bundle.putSerializable("Array_List",responseArrayList)
//                                intent.putExtra("Bundle_Array_List", bundle)
//
//                                startActivity(intent)
                            }
                            RESPONSE_STATE.FAIL->{

                            }
                        }
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


            NaverIdLoginSDK.authenticate(this, oauthLoginCallback)


        }

    }


}
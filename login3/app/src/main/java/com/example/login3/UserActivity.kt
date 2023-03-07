package com.example.login3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.api_project.Model.Item
import com.example.keyword_miner.utils.constant.TAG
import com.example.login3.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserBinding
    var shoplist =ArrayList<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        val bundle = intent.getBundleExtra("Bundle_Array_List")
        if (bundle != null) {
            shoplist= bundle.getSerializable("Array_List") as ArrayList<Item>


        }
        Log.d(TAG, "UserActivity-onCreate() called${shoplist}")
    }
}
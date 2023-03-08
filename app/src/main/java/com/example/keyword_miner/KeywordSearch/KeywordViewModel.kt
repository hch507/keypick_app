package com.example.keyword_miner.KeywordSearch

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.keyword_miner.KeywordInfo
import com.example.keyword_miner.Model.ItemPeriod
import com.example.keyword_miner.Model.blogData
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant

class KeywordViewModel : ViewModel() {

    private val _currentRelData = MutableLiveData <ArrayList<KeywordInfo>>()
    val currentRelData : LiveData<ArrayList<KeywordInfo>>
        get() = _currentRelData

    private val _currentBlogDate = MutableLiveData<ArrayList<ItemPeriod>>()
    val currentBlogDate : LiveData<ArrayList<ItemPeriod>>
        get() = _currentBlogDate

    private val _currentMonthCnt = MutableLiveData<ArrayList<blogData>>()
    val currentMonthCnt : LiveData<ArrayList<blogData>>
        get() = _currentMonthCnt
//    init{
//        _currentRelData.value= ArrayList<KeywordInfo>()
//        _currentBlogDate.value=ArrayList<ItemPeriod>()
//        _currentMonthCnt.value=ArrayList<blogData>()
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateKeywordData(searchTerm : String){
        //연관검색어
        RetrofitManager.instance.searchKeywordRel(searchTerm = searchTerm) { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d("HCH", "api 호출에 성공하였습니다 ${responseArrayList?.get(0)}")
                    _currentRelData.value=responseArrayList

                }
                RESPONSE_STATE.FAIL -> {

                    Log.d(constant.TAG, "api 호충에 실패 하였습니다")
                }
            }
        }

        RetrofitManager.instance.searcKData(searchTerm = searchTerm) { responseState, responseData ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d("HCH", "api 호출에 성공하였습니다 ${responseData}")
                    _currentBlogDate.value= responseData as ArrayList<ItemPeriod>?
                }
                RESPONSE_STATE.FAIL -> {

                    Log.d(constant.TAG, "api 호충에 실패 하였습니다")
                }
            }
        }

        RetrofitManager.instance.searchBlogCnt(searchTerm = searchTerm) { responseState, responseData ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d("HCH", "api 호출에 성공하였습니다 ${responseData}")



                    _currentMonthCnt.value=responseData

                }
                RESPONSE_STATE.FAIL -> {

                    Log.d(constant.TAG, "api 호충에 실패 하였습니다")
                    Log.d("LHH", "api 호충에 실패 하였습니다")

                }

            }

        }
    }

}
package com.keyword.keyword_miner.ui.viewmodels

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keyword.keyword_miner.KeywordInfo
import com.keyword.keyword_miner.domain.Model.ItemPeriod
import com.keyword.keyword_miner.domain.Model.blogData
import com.keyword.keyword_miner.data.Retrofit.RetrofitManager
import com.keyword.keyword_miner.ui.App
import com.keyword.keyword_miner.utils.Blog_API
import com.keyword.keyword_miner.utils.RESPONSE_STATE
import com.keyword.keyword_miner.utils.constant

class KeywordViewModel: ViewModel() {
    val context: Context = App.instance.getAppContext()
    private val _currentRelData = MutableLiveData <ArrayList<KeywordInfo>>()
    val currentRelData : LiveData<ArrayList<KeywordInfo>>
        get() = _currentRelData

    private val _currentBlogDate = MutableLiveData<ArrayList<ItemPeriod>>()
    val currentBlogDate : LiveData<ArrayList<ItemPeriod>>
        get() = _currentBlogDate

    private val _currentMonthCnt = MutableLiveData<ArrayList<blogData>>()
    val currentMonthCnt : LiveData<ArrayList<blogData>>
        get() = _currentMonthCnt




    @RequiresApi(Build.VERSION_CODES.O)
    fun updateKeywordData(searchTerm : String){
        Log.d("aaaa4", "KeywordViewModel - updateKeywordData() - called${searchTerm}")
        RetrofitManager.instance.searchKeywordRel(searchTerm = searchTerm) { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
//

                    if(responseArrayList!!.isEmpty()){

                        Log.d("bbbb", "KeywordViewModel - updateKeywordData() - called ${responseArrayList}")
                        Toast.makeText( context,"잠시후 다시 검색해주세요", Toast.LENGTH_SHORT).show()
//                        System.exit(0);
                    }else {
                        _currentRelData.value = responseArrayList
                    }

                }
                RESPONSE_STATE.FAIL -> {

                    Log.d(constant.TAG, "api 호충에 실패 하였습니다")
                }
            }
        }

        RetrofitManager.instance.searcKData(searchTerm = searchTerm) { responseState, responseData ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d("aaaa5", "api 호출에 성공하였습니다 ${responseData}")
                    _currentBlogDate.value= responseData as ArrayList<ItemPeriod>?
                }
                RESPONSE_STATE.FAIL -> {

                    Log.d(constant.TAG, "api 호충에 실패 하였습니다")
                }
            }
        }

        RetrofitManager.instance.searchBlogCnt(searchTerm = searchTerm, sort = Blog_API.SORT) { responseState, responseData ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d("aaaa6", "api 호출에 성공하였습니다 ${responseData}")
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
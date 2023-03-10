package com.example.keyword_miner.User

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.keyword_miner.KeywordInfo
import com.example.keyword_miner.Model.MyBlogData
import com.example.keyword_miner.Model.UserBlog
import com.example.keyword_miner.Retrofit.RetrofitManager
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class UserBlogViewmodel : ViewModel() {

    private val _currentBlogData = MutableLiveData <UserBlog>()
    val currentBlogData : LiveData<UserBlog>
        get() = _currentBlogData

    private val _currentUserBlogCnt = MutableLiveData<List<MyBlogData>>()
    val currentUserBLogCnt : LiveData<List<MyBlogData>>
        get() = _currentUserBlogCnt

    fun UserSet(email : String, name : String){
        val User = UserBlog(email,name)
        _currentBlogData.value =User
    }
    fun BlogCntUpdate(username:String){
        RetrofitManager.instance.blogData(email = username) { responseState, responsebody ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d("HHH", "api 호출에 성공하였습니다 ${responsebody}")

                    val doc = Jsoup.parse(responsebody, "", Parser.xmlParser())
                    val visitorCnts = doc.getElementsByTag("visitorcnt")

                    val idList = mutableListOf<String>()
                    val cntList = mutableListOf<Double>()

                    for (visitorCnt in visitorCnts) {
                        val id = visitorCnt.attr("id")
                        val cnt = visitorCnt.attr("cnt")?.toDoubleOrNull() ?: 0.0
                        idList.add(id)
                        cntList.add(cnt)

                    }
                    _currentUserBlogCnt.value= listOf(MyBlogData("최근 5일 방문자",idList,cntList))
                    Log.d("HHH", "UserBlogViewmodel-BlogCntUpdate() called${idList}")
                    Log.d("HHH", "UserBlogViewmodel-BlogCntUpdate() called${cntList}")

                }
                RESPONSE_STATE.FAIL -> {

                    Log.d(constant.TAG, "api 호충에 실패 하였습니다")
                }
            }
        }
    }

}
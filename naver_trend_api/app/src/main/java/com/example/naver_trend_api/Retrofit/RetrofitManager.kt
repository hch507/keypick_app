package com.example.keyword_miner.Retrofit

import android.util.Log
import com.example.chatgpt_api.Retrofit.ChatGPTRequest
import com.example.chatgpt_api.Retrofit.Keyword

import com.example.keyword_miner.utils.API

import com.example.keyword_miner.utils.RESPONSE_STATE

import com.example.keyword_miner.utils.constant.TAG
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }
    private var iRetrofit : IRetrofit? = RetrofitClient.getRetrifitClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE) -> Unit){
        var item : String? = searchTerm

        var keywordGroups= listOf(item, listOf(item))
        val request = ChatGPTRequest(API.start_date, API.end_date, API.timeunit,
            keywordGroups as List<Keyword>
        )
        val call = iRetrofit?.getRelKwdStat(API.Content_Type,API.Client_id, API.Client_pw,request).let{
            it
        }?: return
        //실제 요청 후 callback을 받
        call.enqueue(object:retrofit2.Callback<JsonElement>{
            //응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                Log.d(TAG, "RetrofitManager-onResponse() called / response : ${response.body()}")
//                var parseDataArray = ArrayList<KeywordInfo>()
//                response.body()?.let{
//                    val body = it.asJsonObject
//                    val results = body.getAsJsonArray("keywordList")
//
//                    results.forEach{
//                            item->
//                        val itemObject = item.asJsonObject
//                        // 키워드 네임
//                        val relkeyword = itemObject.get("relKeyword").asString
//                        //pc클릭수 가져오기
//                        val monthlyPcQcCnt = itemObject.get("monthlyPcQcCnt").asString
//                        //모바일 클릭수 가져오기
//                        val monthlyMobileQcCnt = itemObject.get("monthlyMobileQcCnt").asString
//
//
//                        val keywordItem = KeywordInfo(relKeyword = relkeyword, monthlyPcQcCnt = monthlyPcQcCnt, monthlyMobileQcCnt = monthlyMobileQcCnt)
//                        parseDataArray.add(keywordItem)
//                    }
//
//                }
                completion(RESPONSE_STATE.OKAY)
            }
            //응답실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

                Log.d(TAG, "RetrofitManager-onFailure() called/t:$t")
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }
}
package com.example.keyword_miner.Retrofit

import android.util.Log
import com.example.chatgpt_api.Retrofit.ChatGPTRequest

import com.example.keyword_miner.utils.API

import com.example.keyword_miner.utils.RESPONSE_STATE

import com.example.keyword_miner.utils.constant.TAG
import com.example.naver_trend_api.Model.ItemPeriod
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }
    private var iRetrofit : IRetrofit? = RetrofitClient.getRetrifitClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, Any?) -> Unit){
        var item : String? = searchTerm

        val keywordGroups = listOf(
            mapOf("groupName" to item, "keywords" to listOf(item))
        )
        val request = ChatGPTRequest(API.start_date, API.end_date, API.timeunit,
            keywordGroups as List<Map<String, String?>>
        )
        Log.d(TAG, "RetrofitManager-searchPhotos() called${request}")
        val call = iRetrofit?.getRelKwdStat(API.Content_Type,API.Client_id, API.Client_pw,request).let{
            it
        }?: return
        //실제 요청 후 callback을 받
        call.enqueue(object:retrofit2.Callback<JsonElement>{
            //응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                Log.d(TAG, "RetrofitManager-onResponse() called ")
                var parseDataArray = ArrayList<ItemPeriod>()
                var parsePeriod = ArrayList<String>()
                var parseRate = ArrayList<Double>()
                response.body()?.let{
                    val body = it.asJsonObject
                    val results = body.getAsJsonArray("results")

                    results.forEach{
                            item->
                            val itemObject = item.asJsonObject
                            // 키워드 네임
                            val title = itemObject.get("title").asString
                            //pc클릭수 가져오기
                            val data = itemObject.getAsJsonArray("data")

                        data.forEach{
                            date ->
                            val dateObjects = date.asJsonObject
                            // period
                            val period = dateObjects.get("period").asString
                            val ratio = dateObjects.get(("ratio")).asDouble
                            parsePeriod.add(period)
                            parseRate.add((ratio))
                        }

                        val ItemData= ItemPeriod(title = title, period = parsePeriod, rate = parseRate)
                        parseDataArray.add(ItemData)
                    }

                }
                completion(RESPONSE_STATE.OKAY,parseDataArray)
            }
            //응답실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

                Log.d(TAG, "RetrofitManager-onFailure() called/t:$t")
                completion(RESPONSE_STATE.FAIL,null)
            }
        })
    }
}
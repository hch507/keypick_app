package com.example.keyword_miner.Retrofit

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.api_project.Model.Item
import com.example.keyword_miner.utils.Blog_API
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.constant.TAG

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }

    private var iRetrofit_blog : IRetrofit? = RetrofitClient.getRetrifitClient(Blog_API.BASE_URL)?.create(IRetrofit::class.java)


    //검색어에 대한 총 블로그 수
    fun search(header : String, completion :(RESPONSE_STATE, ArrayList<Item>?) -> Unit){
        var parseBlogDataArray = ArrayList<Item>()
        var parseDate = ArrayList<String>()

        val call = iRetrofit_blog?.getBlogCnt(authorization = header).let{
            it
        }?: return
        //실제 요청 후 callback을 받₩
        call.enqueue(object:retrofit2.Callback<JsonElement>{
            //응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() - called ${header}")
                Log.d(TAG, "RetrofitManager-onResponse() called ${response.body()}")

//                response.body()?.let{
//                    val body = it.asJsonObject
//                    val total = body.get("total").asString
//                    val results = body.getAsJsonArray("items")

//                    results.forEach{
//                            item->
//                        val itemObject = item.asJsonObject
//                        // 키워드 네임
//                        val postdate = itemObject.get("postdate").asString
//                        //pc클릭수 가져오기
//
//                        parseDate.add(postdate)
//                    }
//                    val ItemData= blogData(total = total, data = parseDate)
//                    parseBlogDataArray.add(ItemData)
     //           }
                completion(RESPONSE_STATE.OKAY,parseBlogDataArray)
            }
            //응답실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

                Log.d(TAG, "RetrofitManager-onFailure() called/t:$t")
                completion(RESPONSE_STATE.FAIL,null)
            }
        })

    }
}
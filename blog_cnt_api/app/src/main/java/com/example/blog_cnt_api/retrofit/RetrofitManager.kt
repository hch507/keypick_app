package com.example.unsplash_app_tutorial.retrofit

import android.util.Log
import com.example.blog_cnt_api.Model.blogData
import com.example.unsplash_app_tutorial.utils.API
import com.example.unsplash_app_tutorial.utils.Constant.TAG
import com.example.unsplash_app_tutorial.utils.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    companion object{
        val instance = RetrofitManager()
    }
    //레트로핏 인터페이스 가져오기
    private var iRetrofit : IRetrofit? =RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //api호출
    fun searchPhotos(searchTerm : String?, completion :(RESPONSE_STATE, ArrayList<blogData>?) -> Unit){
        var parseDataArray = ArrayList<blogData>()
        var parseDate = ArrayList<String>()

        val call = iRetrofit?.searchPhotos(client_id = API.CLIENT_ID, client_secret = API.CLIENT_PW , display = 100 ,searhTerm =searchTerm, sort=API.SORT).let{
            it
        }?: return
        //실제 요청 후 callback을 받₩
        call.enqueue(object:retrofit2.Callback<JsonElement>{
            //응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                Log.d(TAG, "RetrofitManager-onResponse() called ")
                
                response.body()?.let{
                    val body = it.asJsonObject
                    val total = body.get("total").asString
                    val results = body.getAsJsonArray("items")

                    results.forEach{
                            item->
                        val itemObject = item.asJsonObject
                        // 키워드 네임
                        val postdate = itemObject.get("postdate").asString
                        //pc클릭수 가져오기

                        parseDate.add(postdate)
                    }
                    val ItemData= blogData(total = total, data = parseDate)
                    parseDataArray.add(ItemData)
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
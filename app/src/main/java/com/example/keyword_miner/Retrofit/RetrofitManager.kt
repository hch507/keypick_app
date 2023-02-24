package com.example.keyword_miner.Retrofit

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.keyword_miner.KeywordInfo
import com.example.keyword_miner.utils.API
import com.example.keyword_miner.utils.RESPONSE_STATE
import com.example.keyword_miner.utils.Signature
import com.example.keyword_miner.utils.constant.TAG
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }
    private var iRetrofit : IRetrofit? = RetrofitClient.getRetrifitClient(API.BASE_URL)?.create(IRetrofit::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, ArrayList<KeywordInfo>?) -> Unit){

        val call = iRetrofit?.getRelKwdStat(
            content_type = API.Content_Type,
            x_timestamp = API.X_Timestamp,
            api_key = API.X_API_KEY,
            x_customer = API.X_customer,
            x_signature = Signature.generate(API.X_Timestamp,
                                             Signature.method,
                                             Signature.uri,
                                             API.X_secret

            ), hintKeywords = searchTerm).let{
            it
        }?: return
        //실제 요청 후 callback을 받
        call.enqueue(object:retrofit2.Callback<JsonElement>{
            //응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                Log.d(TAG, "RetrofitManager-onResponse() called / response : ${response.body()}")
                var parseDataArray = ArrayList<KeywordInfo>()
                response.body()?.let{
                    val body = it.asJsonObject
                    val results = body.getAsJsonArray("keywordList")

                    results.forEach{
                            item->
                        val itemObject = item.asJsonObject
                        // 키워드 네임
                        val relkeyword = itemObject.get("relKeyword").asString
                        //pc클릭수 가져오기
                        val monthlyPcQcCnt = itemObject.get("monthlyPcQcCnt").asString
                        //모바일 클릭수 가져오기
                        val monthlyMobileQcCnt = itemObject.get("monthlyMobileQcCnt").asString


                        val keywordItem = KeywordInfo(relKeyword = relkeyword, monthlyPcQcCnt = monthlyPcQcCnt, monthlyMobileQcCnt = monthlyMobileQcCnt)
                        parseDataArray.add(keywordItem)
                    }

                }
                completion(RESPONSE_STATE.OKAY, parseDataArray)
            }
            //응답실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

                Log.d(TAG, "RetrofitManager-onFailure() called/t:$t")
                completion(RESPONSE_STATE.FAIL, null)
            }

        })
    }
}
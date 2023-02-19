package com.example.keyword_miner.Retrofit

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE) -> Unit){

        val call = iRetrofit?.getRelKwdStat(content_type = API.Content_Type, x_timestamp = API.X_Timestamp, api_key = API.X_API_KEY, x_customer = API.X_customer, x_signature = Signature.generate(API.X_Timestamp,
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
//                var parseDataArray = ArrayList<KeywordInfo>()
//                response.body()?.let{
//                    val body = it.asJsonObject
//                    val results = body.getAsJsonArray("results")
//
//                    results.forEach{
//                            item->
//                        val itemObject = item.asJsonObject
//                        val user = itemObject.get("user").asJsonObject
//                        //username 가져오기
//                        val username : String= user.get("username").asString
//                        //likeCount가져오기
//                        val likesCount = itemObject.get("likes").asInt
//                        //thumbnail가져오기
//                        val thumbnaull = itemObject.get("urls").asJsonObject.get("thumb").asString
//                        //creatAt가져오기
//                        val createAt = itemObject.get("created_at").asString
//                        // crrateAt parse
//                        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//                        val formatter = SimpleDateFormat("yyyy년 MM월 dd일")
//                        var outputDate =formatter.format(parser.parse(createAt))
//                        Log.d(TAG, "RetrofitManager-onResponse() called,${outputDate}")
//                        val photoItem = Photo(author = username, likeCount = likesCount, thumbnail = thumbnaull, createAt = createAt)
//                        parseDataArray.add(photoItem)
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
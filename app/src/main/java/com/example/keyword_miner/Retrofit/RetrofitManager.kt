package com.example.keyword_miner.Retrofit

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.keyword_miner.KeywordInfo
import com.example.keyword_miner.Model.*
import com.example.keyword_miner.utils.*
import com.example.keyword_miner.utils.constant.TAG
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }
    private var iRetrofit : IRetrofit? = RetrofitClient.getRetrifitClient(API.BASE_URL)?.create(IRetrofit::class.java)
    private var iRetrofit_search : IRetrofit? = RetrofitClient.getRetrifitClient(Search_API.BASE_URL)?.create(IRetrofit::class.java)
    private var iRetrofit_blog : IRetrofit? = RetrofitClient.getRetrifitClient(Blog_API.BASE_URL)?.create(IRetrofit::class.java)
    private var iRetrofit_user : IRetrofit? = RetrofitClient.getRetrifitClient(MY_BLOG.BASE_URL)?.create(IRetrofit::class.java)
    private var iRetrofit_blog_data : IRetrofit? = RetrofitClient.getGsonRetrifitClient(MY_BLOG.MY_BASE_URL)?.create(IRetrofit::class.java)

    // 연관 검색어 및 월 피씨 모바일 컴생량
    @RequiresApi(Build.VERSION_CODES.O)
    fun searchKeywordRel(searchTerm: String?, completion: (RESPONSE_STATE, ArrayList<KeywordInfo>?) -> Unit){

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

                Log.d(TAG, "RetrofitManager-onResponse() called-rel/ response : ")
                var parseRelArray = ArrayList<KeywordInfo>()
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
                        parseRelArray.add(keywordItem)
                    }

                }
                completion(RESPONSE_STATE.OKAY, parseRelArray)
            }
            //응답실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

                Log.d(TAG, "RetrofitManager-onFailure() called/t:$t")
                completion(RESPONSE_STATE.FAIL, null)
            }

        })
    }


    //검색어에 대한 월별 그래프
    fun searcKData(searchTerm: String?, completion: (RESPONSE_STATE, Any?) -> Unit){
        var item : String? = searchTerm

        val keywordGroups = listOf(
            mapOf("groupName" to item, "keywords" to listOf(item))
        )
        val request = BlogKeywordParam(Search_API.start_date, Search_API.end_date, Search_API.timeunit,
            keywordGroups as List<Map<String, String?>>
        )

        val call = iRetrofit_search?.getKeywordData(API.Content_Type,Search_API.Client_id,Search_API.Client_pw,request).let{
            it
        }?: return
        //실제 요청 후 callback을 받
        call.enqueue(object:retrofit2.Callback<JsonElement>{
            //응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                Log.d(TAG, "RetrofitManager-onResponse-graph() called")
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


    //검색어에 대한 총 블로그 수
    fun searchBlogCnt(searchTerm : String?, sort :String, completion :(RESPONSE_STATE, ArrayList<blogData>?) -> Unit){
        var parseBlogDataArray = ArrayList<blogData>()
        var parseDate = ArrayList<String>()
        var parsetitle = ArrayList<String>()

        val call = iRetrofit_blog?.getBlogCnt(client_id = Blog_API.CLIENT_ID, client_secret = Blog_API.CLIENT_PW , display = 100 ,searhTerm =searchTerm, sort=sort).let{
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
                        val title = itemObject.get("bloggerlink").asString
                        parseDate.add(postdate)
                        parsetitle.add(title)
                    }
                    val ItemData= blogData(total = total, data = parseDate , blogname = parsetitle )
                    Log.d(TAG, "RetrofitManager - onResponse() - called${ItemData}")
                    parseBlogDataArray.add(ItemData)
                }
                completion(RESPONSE_STATE.OKAY,parseBlogDataArray)
            }
            //응답실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

                Log.d(TAG, "RetrofitManager-onFailure() called/t:$t")
                completion(RESPONSE_STATE.FAIL,null)
            }
        })

    }



//    내 블로그 방문자 수
    fun blogData(email : String, completion :(RESPONSE_STATE, String?) -> Unit){
        var parseBlogDataArray = ArrayList<MyBlogData>()


        val call = iRetrofit_blog_data?.getBlogData(blogId = email).let{
            it
        }?: return
        //실제 요청 후 callback을 받₩
        call.enqueue(object:retrofit2.Callback<String>{
            //응답 성공시
            override fun onResponse(call: Call<String>, response: Response<String>) {

                Log.d("HHH", "RetrofitManager-onResponse() called${response.body()}")

                completion(RESPONSE_STATE.OKAY,response.body())
            }
            //응답실패시
            override fun onFailure(call: Call<String>, t: Throwable) {

                Log.d(TAG, "RetrofitManager-onFailure() called/t:$t")
                completion(RESPONSE_STATE.FAIL,null)
            }
        })

    }



}
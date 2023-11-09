package com.keyword.keyword_miner.data.repositoryImpl

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.keyword.keyword_miner.data.Retrofit.NaverRetrofit
import com.keyword.keyword_miner.data.Retrofit.RelSearchRetrofit
import com.keyword.keyword_miner.domain.Model.BlogKeywordParam
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import com.keyword.keyword_miner.utils.API
import com.keyword.keyword_miner.utils.Search_API
import com.keyword.keyword_miner.utils.Signature
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val relApiService :RelSearchRetrofit,
    private val naverApiService : NaverRetrofit
) : KeywordRepository{
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getKeywordRel(searchTerm : String) {
        val response = relApiService.getRelKwdStatTest(
            content_type = API.Content_Type,
            x_timestamp = API.X_Timestamp,
            api_key = API.X_API_KEY,
            x_customer = API.X_customer,
            x_signature = Signature.generate(
                API.X_Timestamp,
                Signature.method,
                Signature.uri,
                API.X_secret),
            hintKeywords = searchTerm)

        val responseRelKeyword = response.body()

        Log.d("hch", "getKeywordRel: ${responseRelKeyword} ")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMonthRatio(searchTerm: String) {

        val keywordGroups = listOf(
            mapOf("groupName" to searchTerm, "keywords" to listOf(searchTerm))
        )
        val request = BlogKeywordParam(
            Search_API.start_date, Search_API.end_date, Search_API.timeunit,
            keywordGroups as List<Map<String, String?>>
        )

        val response = naverApiService.getKeywordData(
            API.Content_Type,
            Search_API.Client_id,
            Search_API.Client_pw,request
        )

        val responseMonthRatio = response.body()
        Log.d("hch", "getKeywordRel: ${responseMonthRatio} ")
    }



}


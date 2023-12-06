package com.keyword.keyword_miner.data.repositoryImpl

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.keyword.keyword_miner.data.Retrofit.NaverRetrofit
import com.keyword.keyword_miner.data.Retrofit.RelSearchRetrofit
import com.keyword.keyword_miner.data.mapper.MainMapper
import com.keyword.keyword_miner.domain.Model.BlogKeywordParam
import com.keyword.keyword_miner.domain.Model.blogTotalData.BlogTotalDataModel
import com.keyword.keyword_miner.domain.Model.monthRadioData.MonthRatioDataModel
import com.keyword.keyword_miner.domain.Model.relKeywordData.RelKeywordDataModel
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import com.keyword.keyword_miner.utils.API
import com.keyword.keyword_miner.utils.Blog_API
import com.keyword.keyword_miner.utils.Search_API
import com.keyword.keyword_miner.utils.Signature
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val relApiService :RelSearchRetrofit,
    private val naverApiService : NaverRetrofit
) : KeywordRepository{
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getKeywordRel(searchTerm : String) : List<RelKeywordDataModel>? {
        API.updateTimestamp()
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

        val responseRelKeyword = response.body()?.keywordList

        Log.d("hch", "getKeywordRel: ${responseRelKeyword} ")
        return responseRelKeyword?.let { MainMapper().mapperToRelkeyword(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMonthRatio(searchTerm: String) : List<MonthRatioDataModel>?{

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

        val responseMonthRatio = response.body()?.results
        Log.d("hch", "getMonthRatio: ${responseMonthRatio} ")

        return responseMonthRatio?.let { MainMapper().mapperToMonthRatio(it) }
    }

    override suspend fun getBlogTotal(searchTerm: String) : BlogTotalDataModel?{
        val response = naverApiService.getBlogTotal(
            client_id = Blog_API.CLIENT_ID,
            client_secret = Blog_API.CLIENT_PW ,
            display = 100 ,searhTerm =searchTerm,
            sort=Blog_API.SORT
        )

        val responseBlogTotal = response.body()

        Log.d("hch", "getBlogTotal: ${responseBlogTotal} ")
        return responseBlogTotal?.let { MainMapper().mapperToBlogTotal(it) }
    }


}


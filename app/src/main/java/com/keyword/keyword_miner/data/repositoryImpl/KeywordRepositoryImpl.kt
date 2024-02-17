package com.keyword.keyword_miner.data.repositoryImpl

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.keyword.keyword_miner.data.Retrofit.NaverRetrofit
import com.keyword.keyword_miner.data.Retrofit.RelSearchRetrofit
import com.keyword.keyword_miner.data.mapper.MainMapper
import com.keyword.keyword_miner.data.dto.BlogKeywordParam
import com.keyword.keyword_miner.domain.model.blogTotalData.BlogTotalDataModel
import com.keyword.keyword_miner.domain.model.monthRadioData.MonthRatioDataModel
import com.keyword.keyword_miner.domain.model.rankData.RankDataModel
import com.keyword.keyword_miner.domain.model.relkeyworddata.RelKeywordDataModel
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import com.keyword.keyword_miner.utils.API
import com.keyword.keyword_miner.utils.Blog_API
import com.keyword.keyword_miner.utils.Search_API
import com.keyword.keyword_miner.utils.Signature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val relApiService: RelSearchRetrofit,
    private val naverApiService: NaverRetrofit
) : KeywordRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getKeywordRel(searchTerm: String): Flow<List<RelKeywordDataModel>?> =
        flow {
            API.updateTimestamp()
            Log.d("hchch", "getKeywordRel: ${API.X_Timestamp} ")
            try {
                emit(relApiService.getRelKwdStatTest(
                    content_type = API.Content_Type,
                    x_timestamp = API.X_Timestamp,
                    api_key = API.X_API_KEY,
                    x_customer = API.X_customer,
                    x_signature = Signature.generate(
                        API.X_Timestamp,
                        Signature.method,
                        Signature.uri,
                        API.X_secret
                    ),
                    hintKeywords = searchTerm
                ).body()?.keywordList?.let {
                    MainMapper().mapperToRelkeyword(it)
                })

            } catch (e: IOException) {
                emptyList<RelKeywordDataModel>()
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMonthRatio(searchTerm: String): Flow<List<MonthRatioDataModel>?> =
        flow {
            val keywordGroups = listOf(
                mapOf("groupName" to searchTerm, "keywords" to listOf(searchTerm))
            )
            val request = BlogKeywordParam(
                Search_API.start_date, Search_API.end_date, Search_API.timeunit,
                keywordGroups as List<Map<String, String?>>
            )
            Log.d("hhh", "getMonthRatiobody:${request} ")
            try {
                emit(
                    naverApiService.getKeywordData(
                        API.Content_Type,
                        Search_API.Client_id,
                        Search_API.Client_pw, request
                    ).body()?.results.let { MainMapper().mapperToMonthRatio(it!!) })
            } catch (e: IOException) {
                emptyList<MonthRatioDataModel>()
            }
        }

    override suspend fun getBlogTotal(searchTerm: String): Flow<BlogTotalDataModel?> = flow {
        try {
            emit(naverApiService.getBlogTotal(
                client_id = Blog_API.CLIENT_ID,
                client_secret = Blog_API.CLIENT_PW,
                display = 100, searhTerm = searchTerm,
                sort = Blog_API.SORT
            ).body().let { MainMapper().mapperToBlogTotal(it!!) })
        }catch (e: IOException){
            emptyList<BlogTotalDataModel>()
        }
    }


    override suspend fun getBlogRank(searchTerm: String): Flow<RankDataModel?> = flow {
        try {
            emit(
                naverApiService.getBlogTotal(
                    client_id = Blog_API.CLIENT_ID,
                    client_secret = Blog_API.CLIENT_PW,
                    display = 100, searhTerm = searchTerm,
                    sort = Blog_API.SORT2
                ).body().let { MainMapper().mapperToBlogRank(it!!) }
            )
            Log.d("Rank", "KeywordRepositoryImpl - getBlogRank() - ${
                naverApiService.getBlogTotal(
                    client_id = Blog_API.CLIENT_ID,
                    client_secret = Blog_API.CLIENT_PW,
                    display = 100, searhTerm = searchTerm,
                    sort = Blog_API.SORT2
                ).body()
            }")
        }catch (e:IOException){
            emptyList<RankDataModel>()
        }
    }
}


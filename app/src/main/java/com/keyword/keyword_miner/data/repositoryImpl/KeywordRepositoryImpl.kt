package com.keyword.keyword_miner.data.repositoryImpl

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.keyword.keyword_miner.data.Retrofit.IRetrofit
import com.keyword.keyword_miner.data.Retrofit.RelSearchRetrofit
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import com.keyword.keyword_miner.utils.API
import com.keyword.keyword_miner.utils.Signature
import retrofit2.Call
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val apiService :RelSearchRetrofit
) : KeywordRepository{
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getKeywordRel(searchTerm : String) {
        val response = apiService.getRelKwdStatTest(
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

    override suspend fun getMonthRatio() {
        TODO("Not yet implemented")
    }

}


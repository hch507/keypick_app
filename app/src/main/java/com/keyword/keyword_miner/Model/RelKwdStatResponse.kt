package com.keyword.keyword_miner

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class KeywordInfo(
    @SerializedName("relKeyword") val relKeyword: String,
    @SerializedName("monthlyPcQcCnt") val monthlyPcQcCnt: String,
    @SerializedName("monthlyMobileQcCnt") val monthlyMobileQcCnt: String,
):Serializable


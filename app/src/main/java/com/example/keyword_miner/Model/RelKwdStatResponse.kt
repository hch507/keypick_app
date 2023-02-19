package com.example.keyword_miner

import com.google.gson.annotations.SerializedName


data class KeywordInfo(
    @SerializedName("relKeyword") val relKeyword: String,
    @SerializedName("monthlyPcQcCnt") val monthlyPcQcCnt: Int,
    @SerializedName("monthlyMobileQcCnt") val monthlyMobileQcCnt: Int,
    @SerializedName("monthlyAvePcClkCnt") val monthlyAvePcClkCnt: Float,
    @SerializedName("monthlyAveMobileClkCnt") val monthlyAveMobileClkCnt: Float,
    @SerializedName("monthlyAvePcCtr") val monthlyAvePcCtr: Float,
    @SerializedName("monthlyAveMobileCtr") val monthlyAveMobileCtr: Float
)
package com.example.keyword_miner

import com.google.gson.annotations.SerializedName


data class KeywordInfo(
    @SerializedName("relKeyword") val relKeyword: String,
    @SerializedName("monthlyPcQcCnt") val monthlyPcQcCnt: String,
    @SerializedName("monthlyMobileQcCnt") val monthlyMobileQcCnt: String,
)
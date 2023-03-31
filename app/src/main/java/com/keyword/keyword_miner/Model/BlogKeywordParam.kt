package com.keyword.keyword_miner.Model

import com.google.gson.annotations.SerializedName

data class BlogKeywordParam(
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("timeUnit") val timeUnit: String,
    @SerializedName("keywordGroups") var keywordGroups: List<Map<String, String?>>
)

package com.keyword.keyword_miner.domain.Model

import com.google.gson.annotations.SerializedName

data class RelKeywordInfo (
    val relKeyword: String,
    val monthlyPcQcCnt: String,
    val monthlyMobileQcCnt: String
)


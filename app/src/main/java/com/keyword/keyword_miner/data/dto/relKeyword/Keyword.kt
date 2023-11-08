package com.keyword.keyword_miner.data.dto.relKeyword

data class Keyword(
    val compIdx: String,
    val monthlyAveMobileClkCnt: String,
    val monthlyAveMobileCtr: String,
    val monthlyAvePcClkCnt: String,
    val monthlyAvePcCtr: String,
    val monthlyMobileQcCnt: String,
    val monthlyPcQcCnt: String,
    val plAvgDepth: String,
    val relKeyword: String
)
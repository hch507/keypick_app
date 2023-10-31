package com.keyword.keyword_miner.data.dto.relKeyword

data class Keyword(
    val compIdx: String,
    val monthlyAveMobileClkCnt: Double,
    val monthlyAveMobileCtr: Double,
    val monthlyAvePcClkCnt: Double,
    val monthlyAvePcCtr: Double,
    val monthlyMobileQcCnt: Int,
    val monthlyPcQcCnt: Int,
    val plAvgDepth: Int,
    val relKeyword: String
)
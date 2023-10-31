package com.keyword.keyword_miner.data.dto.monthRatio

import com.keyword.keyword_miner.data.dto.monthRatio.Data

data class Result(
    val data: List<Data>,
    val keywords: List<String>,
    val title: String
)
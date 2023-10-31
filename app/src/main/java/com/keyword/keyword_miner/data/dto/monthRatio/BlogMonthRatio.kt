package com.keyword.keyword_miner.data.dto.monthRatio

data class BlogMonthRatio(
    val endDate: String,
    val results: List<Result>,
    val startDate: String,
    val timeUnit: String
)
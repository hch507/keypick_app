package com.keyword.keyword_miner.data.dto.blogTotal

data class BlogTotal(
    val display: Int,
    val items: List<Item>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)
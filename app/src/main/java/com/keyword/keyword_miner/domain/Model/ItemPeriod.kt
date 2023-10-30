package com.keyword.keyword_miner.domain.Model

import java.io.Serializable

data class ItemPeriod(
    var title : String,
    var period: ArrayList<String>,
    var rate : ArrayList<Double>
)

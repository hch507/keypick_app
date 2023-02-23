package com.example.naver_trend_api.Model

import java.io.Serializable

data class ItemPeriod(
    var title : String,
    var period: ArrayList<String>,
    var rate : ArrayList<Double>
):Serializable

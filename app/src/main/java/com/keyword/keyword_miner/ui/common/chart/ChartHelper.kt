package com.keyword.keyword_miner.ui.common.chart

import android.content.Context

interface ChartHelper<T> {
    fun setChart(chart : T, context: Context, cnt : List<Double>, period: List<String>)
}
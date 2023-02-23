package com.example.chatgpt_api.Retrofit

import com.google.gson.annotations.SerializedName


data class ChatGPTRequest(

    @SerializedName ("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("timeUnit") val timeUnit: String,
    @SerializedName("keywordGroups") var keywordGroups: List<Map<String, String?>>
)

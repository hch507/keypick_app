package com.example.chatgpt_api.Retrofit

import com.google.gson.annotations.SerializedName

data class ChatGPTRequest(
    @SerializedName ("model") val model :String,
    @SerializedName ("prompt") val prompt: String,
    @SerializedName ("max_tokens") val maxTokens: Int,
    @SerializedName ("temperature") val temperature: Double,
    @SerializedName ("stop") val stop: List<String>
)

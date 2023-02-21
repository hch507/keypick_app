package com.example.keyword_miner.utils
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.keyword_miner.utils.constant.TAG
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.util.*
import kotlin.text.Charsets.UTF_8

object constant {
    val TAG : String ="로그"
}

object API {
    const val BASE_URL : String = "https://api.openai.com/v1/"
    const val token ="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"
    const val temperature = 0.7
    const val maxTokens = 300
    const val model ="text-davinci-003"
    val stop = listOf("===")

}

enum class RESPONSE_STATE{
    OKAY,
    FAIL
}


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


object Blog_API{
    const val BASE_URL : String = "https://openapi.naver.com/v1/"

}


enum class RESPONSE_STATE{
    OKAY,
    FAIL
}

object Signature {
    val method ="GET"
    val uri="/keywordstool"
    @RequiresApi(Build.VERSION_CODES.O)
    fun generate(timestamp: String, method: String, uri: String, secretKey: String): String {
        Log.d(TAG, "Signature - generate() - called")
        val message = "$timestamp.$method.$uri"
        val secretKeyBytes = secretKey.toByteArray(UTF_8)
        val messageBytes = message.toByteArray(UTF_8)
        val signingKey = SecretKeySpec(secretKeyBytes, "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(signingKey)
        val rawHmac = mac.doFinal(messageBytes)
        return Base64.getEncoder().encodeToString(rawHmac)
    }
}
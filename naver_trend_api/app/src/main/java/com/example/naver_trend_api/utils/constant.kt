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

    const val BASE_URL : String = "https://openapi.naver.com/v1/datalab/"
    const val Content_Type: String= "application/json"
    const val Client_id : String ="oKQNT8007_pUD1FBJv0a"
    const val Client_pw : String ="Cp7YEq41hc"
    const val start_date : String ="2020-01-01"
    const val end_date : String ="2023-01-01"
    const val timeunit : String ="month"

}

enum class RESPONSE_STATE{
    OKAY,
    FAIL
}
data class Keyword(val groupName: String, val keywords: List<String>)

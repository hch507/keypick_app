package com.keyword.keyword_miner.utils
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.keyword.keyword_miner.utils.constant.TAG
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.util.*
import kotlin.text.Charsets.UTF_8

object constant {
    val TAG : String ="로그"
}

object API {
    const val BASE_URL : String = "https://api.searchad.naver.com/"
    const val Content_Type : String="application/json"
    var X_Timestamp = System.currentTimeMillis().toString()
    const val X_API_KEY : String = "01000000000c40d694768235be69e873bf7751c0f482f571a4fbe56f728e21f79337f18493"
    const val X_customer : String ="2776436"
    const val X_secret ="AQAAAAAMQNaUdoI1vmnoc793UcD0irzhPxbLpNHDMOxleVvqAA=="

    fun updateTimestamp() {
        X_Timestamp = System.currentTimeMillis().toString()
    }
}

object Search_API {

    const val BASE_URL : String = "https://openapi.naver.com/v1/datalab/"
    const val Content_Type: String= "application/json"
    const val Client_id : String ="oKQNT8007_pUD1FBJv0a"
    const val Client_pw : String ="Cp7YEq41hc"
    const val start_date : String ="2020-01-01"
    const val end_date : String ="2023-06-01"
    const val timeunit : String ="month"

}
object Blog_API{
    const val BASE_URL : String = "https://openapi.naver.com/v1/"

    const val CLIENT_ID ="tZR4mxv0e3Le0j2F3mQP"
    const val CLIENT_PW ="ZJoce5jaT6"
    const val SORT ="date"
    const val SORT2 ="sim"
}


enum class RESPONSE_STATE{
    OKAY,
    FAIL
}
object MY_BLOG{
    const val BASE_URL : String = "https://openapi.naver.com/v1/"
    const val MY_BASE_URL : String ="https://blog.naver.com/"
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
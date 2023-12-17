//package com.keyword.keyword_miner.data.repositoryImpl
//
//import com.keyword.keyword_miner.data.local.sharePref.PreferenceUtil
//import com.keyword.keyword_miner.domain.repository.UserEmailRepository
//import javax.inject.Inject
//
//class UserEmailRepositoryImpl @Inject constructor(
//    private val pref : PreferenceUtil
//)
//: UserEmailRepository {
//    override fun getUserEmailData(): String {
//       return pref.getEmail("userEmail","")
//    }
//
//    override fun saveUserEmailData() {
//        TODO("Not yet implemented")
//    }
//}
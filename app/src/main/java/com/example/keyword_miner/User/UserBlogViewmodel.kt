package com.example.keyword_miner.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.keyword_miner.KeywordInfo
import com.example.keyword_miner.Model.UserBlog

class UserBlogViewmodel : ViewModel() {

    private val _currentBlogData = MutableLiveData <List<UserBlog>>()
    val currentRelData : LiveData<List<UserBlog>>
        get() = _currentBlogData
}
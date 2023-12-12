package com.keyword.keyword_miner.di

import com.keyword.keyword_miner.data.repositoryImpl.KeywordRepositoryImpl
import com.keyword.keyword_miner.data.repositoryImpl.UserBlogRepositoryImpl
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import com.keyword.keyword_miner.domain.repository.UserBLogRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModules {

    @Binds
    abstract fun UserBLogRepository(repositoryImpl: UserBlogRepositoryImpl): UserBLogRepository
    @Binds
    abstract fun keywordRepository(repositoryImpl: KeywordRepositoryImpl):KeywordRepository
}
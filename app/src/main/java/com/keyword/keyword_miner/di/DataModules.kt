package com.keyword.keyword_miner.di

import com.keyword.keyword_miner.data.repositoryImpl.LoginRepositoryImpl
import com.keyword.keyword_miner.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModules {

    @Binds
    abstract fun LoginRepository(repositoryImpl: LoginRepositoryImpl):LoginRepository
}
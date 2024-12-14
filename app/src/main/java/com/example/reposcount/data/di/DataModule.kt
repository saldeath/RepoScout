package com.example.reposcount.data.di

import com.example.reposcount.data.network.ConnectivityServiceImpl
import com.example.reposcount.data.repos.ReposRepositoryImpl
import com.example.reposcount.domain.network.data.ConnectivityService
import com.example.reposcount.domain.repos.data.ReposRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindReposRepository(reposRepositoryImpl: ReposRepositoryImpl) : ReposRepository

    @Binds
    fun bindConnectivityService(connectivityServiceImpl: ConnectivityServiceImpl): ConnectivityService
}
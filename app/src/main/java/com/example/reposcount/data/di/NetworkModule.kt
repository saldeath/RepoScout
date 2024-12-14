package com.example.reposcount.data.di

import com.example.reposcount.data.repos.remote.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val URL = "https://api.github.com/users/abnamrocoesd/"

    @Provides
    @Singleton
    fun provideGithubService(): GithubService {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GithubService::class.java)
    }
}
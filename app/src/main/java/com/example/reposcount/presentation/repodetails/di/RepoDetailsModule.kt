package com.example.reposcount.presentation.repodetails.di

import com.example.reposcount.presentation.repodetails.textProvider.RepoDetailsTextProvider
import com.example.reposcount.presentation.repodetails.textProvider.RepoDetailsTextProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepoDetailsModule {

    @Binds
    fun bindTextProvider(repoDetailsTextProviderImpl: RepoDetailsTextProviderImpl): RepoDetailsTextProvider
}
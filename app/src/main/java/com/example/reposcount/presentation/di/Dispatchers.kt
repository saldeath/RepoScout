package com.example.reposcount.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Dispatchers {
    @Module
    @InstallIn(SingletonComponent::class)
    object DispatchersModule {
        @Provides
        @Dispatcher(RSDispatchers.IO)
        fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}
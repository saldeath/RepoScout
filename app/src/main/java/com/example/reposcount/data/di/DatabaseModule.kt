package com.example.reposcount.data.di

import android.content.Context
import androidx.room.Room
import com.example.reposcount.data.repos.local.RepoScoutDatabase
import com.example.reposcount.data.repos.local.RepositoriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): RepoScoutDatabase {
        return Room
            .databaseBuilder(context, RepoScoutDatabase::class.java, "repo_scout.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRepositoriesDao(appDatabase: RepoScoutDatabase): RepositoriesDao {
        return appDatabase.repositoriesDao()
    }
}
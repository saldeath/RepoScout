package com.example.reposcount.data.repos.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [RepositoryDataModel::class],
    version = 1,
    exportSchema = false,
)
abstract class RepoScoutDatabase : RoomDatabase() {

    abstract fun repositoriesDao(): RepositoriesDao
}
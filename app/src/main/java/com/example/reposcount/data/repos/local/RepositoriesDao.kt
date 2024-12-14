package com.example.reposcount.data.repos.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoriesDao {

    @Insert
    suspend fun insertRepositories(repositories: List<RepositoryDataModel>)

    @Query("DELETE FROM repositories")
    suspend fun deleteAllRepositories()

    @Transaction
    suspend fun deleteAndInsertRepositories(repositories: List<RepositoryDataModel>) {
        deleteAllRepositories()
        insertRepositories(repositories)
    }

    @Query("SELECT * FROM repositories")
    fun getRepositories(): Flow<List<RepositoryDataModel>>

    @Query("SELECT * FROM repositories WHERE githubId = :githubId")
    fun getRepoDetails(githubId: Int): Flow<RepositoryDataModel>

    @Query("SELECT COUNT(*) FROM repositories")
    suspend fun getTotalRepositories(): Int
}
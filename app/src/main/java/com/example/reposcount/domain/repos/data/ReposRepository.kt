package com.example.reposcount.domain.repos.data

import com.example.reposcount.domain.repos.model.Repository
import kotlinx.coroutines.flow.Flow

interface ReposRepository {

    suspend fun syncRepositories()
    suspend fun syncNextRepositories()
    fun getRepositories() : Flow<List<Repository>>
    fun getRepoDetails(githubId: Int): Flow<Repository>
}
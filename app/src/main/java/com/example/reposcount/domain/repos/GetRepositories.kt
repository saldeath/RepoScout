package com.example.reposcount.domain.repos

import com.example.reposcount.domain.repos.data.ReposRepository
import com.example.reposcount.domain.repos.model.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepositories @Inject constructor(private val reposRepository: ReposRepository) {

    operator fun invoke(): Flow<List<Repository>> {
        return reposRepository.getRepositories()
    }
}
package com.example.reposcount.domain.repos

import com.example.reposcount.domain.repos.data.ReposRepository
import com.example.reposcount.domain.repos.model.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepoDetails @Inject constructor(private val reposRepository: ReposRepository) {

    operator fun invoke(githubId: Int): Flow<Repository> {
        return reposRepository.getRepoDetails(githubId)
    }
}
package com.example.reposcount.domain.repos

import com.example.reposcount.domain.repos.data.ReposRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to synchronize the initial repositories.
 */
class SyncRepositories @Inject constructor(private val reposRepository: ReposRepository) {

    operator fun invoke() = flow {
        emit(reposRepository.syncRepositories())
    }
}
package com.example.reposcount.domain.repos

import com.example.reposcount.domain.repos.data.ReposRepository
import javax.inject.Inject

/**
 * Syncs more repositories.
 */
class SyncNextRepos  @Inject constructor(private val reposRepository: ReposRepository) {

    suspend operator fun invoke() {
        reposRepository.syncNextRepositories()
    }
}
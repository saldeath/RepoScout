package com.example.reposcount.presentation.repos.mapper

import com.example.reposcount.domain.repos.model.Repository
import com.example.reposcount.presentation.repos.model.RepoUiModel
import javax.inject.Inject

class RepoUiMapper @Inject constructor() {

    fun mapToUiModels(repositories: List<Repository>): List<RepoUiModel> {
        return repositories.map {
            mapToUiModel(it)
        }
    }

    private fun mapToUiModel(repository: Repository): RepoUiModel {
        return repository.run {
            RepoUiModel(
                githubId = githubId,
                name = name,
                avatarUrl = avatarUrl,
                visibility = visibility,
                // TODO create a textprovider interface
                private = if (private) "Is private" else "Not private"
            )
        }
    }
}
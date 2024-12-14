package com.example.reposcount.presentation.repodetails.mapper

import com.example.reposcount.domain.repos.model.Repository
import com.example.reposcount.presentation.repodetails.model.RepoDetailsUiModel
import com.example.reposcount.presentation.repodetails.textProvider.RepoDetailsTextProvider
import javax.inject.Inject

class RepoDetailsMapper @Inject constructor(
    private val textProvider: RepoDetailsTextProvider
) {

    fun mapToRepoDetailsUiModel(repository: Repository): RepoDetailsUiModel {
        return repository.run {
            RepoDetailsUiModel(
                githubId = githubId,
                description = description,
                fullName = fullName,
                htmlUrl = repositoryUrl,
                name = name,
                avatarUrl = avatarUrl,
                visibility = visibility,
                private = if (private) textProvider.yes else textProvider.no,
            )
        }
    }
}
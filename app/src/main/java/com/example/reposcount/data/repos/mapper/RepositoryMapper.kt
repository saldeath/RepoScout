package com.example.reposcount.data.repos.mapper

import com.example.reposcount.data.repos.local.RepositoryDataModel
import com.example.reposcount.data.repos.remote.model.RepoResponse
import com.example.reposcount.domain.repos.model.Repository
import javax.inject.Inject

class RepositoryMapper @Inject constructor() {

    fun mapToRepositoryDataModels(repoResponse: List<RepoResponse>): List<RepositoryDataModel> {
        return repoResponse.map {
            mapToRepositoryDataModel(it)
        }
    }

    private fun mapToRepositoryDataModel(repoResponse: RepoResponse): RepositoryDataModel {
        return repoResponse.run {
            RepositoryDataModel(
                id = null,
                githubId = id,
                description = description,
                fullName = fullName,
                htmlUrl = htmlUrl,
                name = name,
                avatarUrl = ownerResponse.avatarUrl,
                visibility = visibility,
                private = private
            )
        }
    }

    fun mapToRepositoryModels(repositoryDataModel: List<RepositoryDataModel>): List<Repository> {
        return repositoryDataModel.map {
            mapToRepositoryModel(it)
        }
    }

    fun mapToRepositoryModel(repositoryDataModel: RepositoryDataModel): Repository {
        return repositoryDataModel.run {
            Repository(
                githubId = githubId,
                description = description,
                fullName = fullName,
                repositoryUrl = htmlUrl,
                name = name,
                avatarUrl = avatarUrl,
                visibility = visibility,
                private = private
            )
        }
    }
}
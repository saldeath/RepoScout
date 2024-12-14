package com.example.reposcount.data.repos.mapper

import com.example.reposcount.data.repos.local.RepositoryDataModel
import com.example.reposcount.data.repos.remote.model.OwnerResponse
import com.example.reposcount.data.repos.remote.model.RepoResponse
import com.example.reposcount.domain.repos.model.Repository
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class RepositoryMapperTest {

    private lateinit var repositoryMapper: RepositoryMapper

    @Before
    fun setUp() {
        repositoryMapper = RepositoryMapper()
    }

    @Test
    fun `map response repo model to repository data model`() {
        // Given
        val repoResponse = RepoResponse(
            id = 1,
            description = "Description 1",
            fullName = "Full Name 1",
            htmlUrl = "http://example.com/1",
            name = "Repo 1",
            ownerResponse = OwnerResponse(avatarUrl = "http://example.com/avatar1"),
            visibility = "public",
            private = false
        )
        val repoResponseList = listOf(repoResponse)
        val expected = listOf(
            RepositoryDataModel(
                id = null,
                githubId = repoResponse.id,
                description = repoResponse.description,
                fullName = repoResponse.fullName,
                htmlUrl = repoResponse.htmlUrl,
                name = repoResponse.name,
                avatarUrl = repoResponse.ownerResponse.avatarUrl,
                visibility = repoResponse.visibility,
                private = repoResponse.private
            )
        )

        // When
        val result = repositoryMapper.mapToRepositoryDataModels(repoResponseList)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `map repository data model to repository domain model`() {
        // Given
        val repoDataModels = RepositoryDataModel(
            githubId = 1,
            description = "Description 1",
            fullName = "Full Name 1",
            htmlUrl = "http://example.com/1",
            name = "Repo 1",
            avatarUrl = "http://example.com/avatar1",
            visibility = "public",
            private = false
        )
        val repoResponseList = listOf(repoDataModels)
        val expected = listOf(
            Repository(
                githubId = repoDataModels.githubId,
                description = repoDataModels.description,
                fullName = repoDataModels.fullName,
                repositoryUrl = repoDataModels.htmlUrl,
                name = repoDataModels.name,
                avatarUrl = repoDataModels.avatarUrl,
                visibility = repoDataModels.visibility,
                private = repoDataModels.private
            )
        )

        // When
        val result = repositoryMapper.mapToRepositoryModels(repoResponseList)

        // Then
        assertEquals(expected, result)
    }
}
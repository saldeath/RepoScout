package com.example.reposcount.data.repos

import com.example.reposcount.data.repos.local.RepositoriesDao
import com.example.reposcount.data.repos.local.RepositoryDataModel
import com.example.reposcount.data.repos.mapper.RepositoryMapper
import com.example.reposcount.data.repos.remote.GithubService
import com.example.reposcount.data.repos.remote.model.RepoResponse
import com.example.reposcount.domain.repos.model.Repository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class ReposRepositoryImplTest {

    private val githubService: GithubService = mockk()
    private val repositoriesDao: RepositoriesDao = mockk()
    private val repositoryMapper: RepositoryMapper = mockk()
    private lateinit var reposRepositoryImpl: ReposRepositoryImpl

    @Before
    fun setUp() {
        reposRepositoryImpl = ReposRepositoryImpl(githubService, repositoriesDao, repositoryMapper)
    }

    @Test
    fun `fully sync the database with repositories`() = runTest {
        // Given
        val repoResponseList = mockk<List<RepoResponse>>()
        val repositoryDataModels = mockk<List<RepositoryDataModel>>()
        coEvery { githubService.getRepos() } returns repoResponseList
        every { repositoryMapper.mapToRepositoryDataModels(repoResponseList) } returns repositoryDataModels
        coEvery { repositoriesDao.deleteAndInsertRepositories(repositoryDataModels) } just Runs

        // When
        reposRepositoryImpl.syncRepositories()

        // Then
        coVerify { githubService.getRepos() }
        verify { repositoryMapper.mapToRepositoryDataModels(repoResponseList) }
        coVerify { repositoriesDao.deleteAndInsertRepositories(repositoryDataModels) }
    }

    @Test
    fun `don't sync if the call to the endpoint fails`() = runTest {
        // Given
        coEvery { githubService.getRepos() } throws Exception("Network Error")
        every { repositoryMapper.mapToRepositoryDataModels(any()) } returns mockk()

        // When
        runCatching { reposRepositoryImpl.syncRepositories() }

        // Then
        coVerify { githubService.getRepos() }
        coVerify(exactly = 0) {
            repositoriesDao.deleteAndInsertRepositories(any())
            repositoryMapper.mapToRepositoryDataModels(any())
        }
    }

    @Test
    fun `fetch and map repositories`() = runTest {
        // Given
        val daoList = mockk<List<RepositoryDataModel>>()
        val mappedList = mockk<List<Repository>>()
        every { repositoriesDao.getRepositories() } returns flowOf(daoList)
        every { repositoryMapper.mapToRepositoryModels(daoList) } returns mappedList

        // When
        val result = reposRepositoryImpl.getRepositories().toList()

        // Then
        assertEquals(listOf(mappedList), result)
        verify { repositoriesDao.getRepositories() }
        verify { repositoryMapper.mapToRepositoryModels(daoList) }
    }

    @Test
    fun `fetch and map repository`() = runTest {
        // Given
        val repoDataModel = mockk<RepositoryDataModel>()
        val repoModel = mockk<Repository>()
        every { repositoriesDao.getRepoDetails(1) } returns flowOf(repoDataModel)
        every { repositoryMapper.mapToRepositoryModel(repoDataModel) } returns repoModel

        // When
        val result = reposRepositoryImpl.getRepoDetails(1).first()

        // Then
        assertEquals(repoModel, result)
        verify { repositoriesDao.getRepoDetails(any()) }
        verify { repositoryMapper.mapToRepositoryModel(repoDataModel) }
    }
}

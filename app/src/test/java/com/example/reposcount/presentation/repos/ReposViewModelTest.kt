package com.example.reposcount.presentation.repos

import app.cash.turbine.test
import com.example.reposcount.domain.network.IsConnectedToInternet
import com.example.reposcount.domain.repos.GetRepositories
import com.example.reposcount.domain.repos.SyncNextRepos
import com.example.reposcount.domain.repos.SyncRepositories
import com.example.reposcount.domain.repos.model.Repository
import com.example.reposcount.presentation.repos.mapper.RepoUiMapper
import com.example.reposcount.presentation.repos.model.RepoUiModel
import com.example.reposcount.presentation.repos.model.RepoUiState
import com.example.reposcount.test.MainCoroutineRule
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ReposViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private val syncRepositories: SyncRepositories = mockk(relaxed = true)
    private val getRepositories: GetRepositories = mockk(relaxed = true)
    private val syncNextRepos: SyncNextRepos = mockk(relaxed = true)
    private val repoUiMapper: RepoUiMapper = mockk(relaxed = true)
    private val isConnectedToInternet: IsConnectedToInternet = mockk(relaxed = true)
    private lateinit var viewModel: ReposViewModel

    private fun createViewModel(): ReposViewModel {
        return ReposViewModel(
            syncRepositories,
            getRepositories,
            syncNextRepos,
            repoUiMapper,
            isConnectedToInternet,
            StandardTestDispatcher()
        )
    }

    @Test
    fun `syncRepos when there is an internet connection`() = runTest {
        // Given
        coEvery { isConnectedToInternet() } returns flowOf(true)
        coEvery { syncRepositories() } returns flowOf(Unit)

        // When
        viewModel = createViewModel()

        // Then
        viewModel.uiState.test {
            assertEquals(RepoUiState(isLoading = true), awaitItem())
            assertEquals(RepoUiState(isLoading = false), awaitItem())
        }
        coVerify { syncRepositories() }
    }

    @Test
    fun `don't syncRepos when there is no internet connection`() = runTest {
        // Given
        coEvery { isConnectedToInternet() } returns flowOf(false)
        coEvery { syncRepositories() } returns flowOf(Unit)

        // When
        viewModel = createViewModel()

        // Then
        viewModel.uiState.test {
            assertEquals(RepoUiState(isLoading = true), awaitItem())
            assertEquals(RepoUiState(isLoading = false), awaitItem())
        }
        coVerify { syncRepositories wasNot Called }
    }

    @Test
    fun `hide loading when syncing fails`() = runTest {
        // Given
        coEvery { isConnectedToInternet() } returns flowOf(false)
        coEvery { syncRepositories() } throws RuntimeException()

        // When
        viewModel = createViewModel()

        // Then
        viewModel.uiState.test {
            assertEquals(RepoUiState(isLoading = true), awaitItem())
            assertEquals(RepoUiState(isLoading = false), awaitItem())
        }
        coVerify { syncRepositories wasNot Called }
    }


    @Test
    fun `test observing the repos`() = runTest {
        // Given
        val repoModels = mockk<List<Repository>>()
        val expectedRepoUiModels = mockk<List<RepoUiModel>>(relaxed = true)
        coEvery { isConnectedToInternet() } returns flowOf(true)
        coEvery { getRepositories() } returns flowOf(repoModels)
        every { repoUiMapper.mapToUiModels(repoModels) } returns expectedRepoUiModels
        coEvery { syncRepositories() } returns flowOf(Unit)

        // When
        viewModel = createViewModel()

        // Then
        advanceUntilIdle()
        assertEquals(RepoUiState(isLoading = false, repoUiModel = expectedRepoUiModels), viewModel.uiState.value)
    }
}
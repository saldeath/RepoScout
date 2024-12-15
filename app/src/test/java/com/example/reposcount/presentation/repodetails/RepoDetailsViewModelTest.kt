package com.example.reposcount.presentation.repodetails

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.reposcount.domain.repos.GetRepoDetails
import com.example.reposcount.domain.repos.model.Repository
import com.example.reposcount.presentation.repodetails.mapper.RepoDetailsMapper
import com.example.reposcount.presentation.repodetails.model.RepoDetailsUiModel
import com.example.reposcount.test.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepoDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private val getRepoDetails: GetRepoDetails = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val repoDetailsMapper: RepoDetailsMapper = mockk()
    private lateinit var viewModel: RepoDetailsViewModel

    @Before
    fun setUp() {
        every { savedStateHandle.getStateFlow("githubId", 1).value } returns 1
    }

    @Test
    fun `getRepoDetails should fetch and update uiState`() = runTest {
        // Given
        val repo = mockk<Repository>()
        val repoDetailsUiModel = mockk<RepoDetailsUiModel>()
        coEvery { getRepoDetails(any()) } returns flowOf(repo)
        every { repoDetailsMapper.mapToRepoDetailsUiModel(repo) } returns repoDetailsUiModel

        // When
        viewModel = RepoDetailsViewModel(getRepoDetails, savedStateHandle, repoDetailsMapper)

        // Then
        viewModel.uiState.test {
            assertEquals(null, awaitItem())
            assertEquals(repoDetailsUiModel, awaitItem())
            coVerify { getRepoDetails(1) }
            verify { repoDetailsMapper.mapToRepoDetailsUiModel(repo) }
        }
    }
}
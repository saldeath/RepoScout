package com.example.reposcount.domain.repos

import com.example.reposcount.domain.repos.data.ReposRepository
import com.example.reposcount.domain.repos.model.Repository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetRepositoriesTest {

    private val reposRepository: ReposRepository = mockk()
    private val getRepositories = GetRepositories(reposRepository)

    @Test
    fun `verify result is returned from the repository`() = runTest {
        // Given
        val expectedFlow: Flow<List<Repository>> = mockk()
        coEvery { reposRepository.getRepositories() } returns expectedFlow

        // When
        val result = getRepositories()

        // Then
        assertEquals(expectedFlow, result)
        verify { reposRepository.getRepositories() }
    }
}
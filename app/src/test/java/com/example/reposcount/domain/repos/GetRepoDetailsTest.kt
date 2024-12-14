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

class GetRepoDetailsTest {

    private val reposRepository: ReposRepository = mockk()
    private val getRepoDetails = GetRepoDetails(reposRepository)

    @Test
    fun `verify result is returned from the repository`() = runTest {
        // Given
        val expectedFlow: Flow<Repository> = mockk()
        val expectedID = 1
        coEvery { reposRepository.getRepoDetails(expectedID) } returns expectedFlow

        // When
        val result = getRepoDetails(expectedID)

        // Then
        assertEquals(expectedFlow, result)
        verify { reposRepository.getRepoDetails(expectedID) }
    }
}
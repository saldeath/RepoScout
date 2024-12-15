package com.example.reposcount.data.repos

import com.example.reposcount.data.repos.local.RepositoriesDao
import com.example.reposcount.data.repos.mapper.RepositoryMapper
import com.example.reposcount.data.repos.remote.GithubService
import com.example.reposcount.domain.repos.data.ReposRepository
import com.example.reposcount.domain.repos.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 10

class ReposRepositoryImpl @Inject constructor(
    private val githubService: GithubService,
    private val repositoriesDao: RepositoriesDao,
    private val repositoryMapper: RepositoryMapper
) : ReposRepository {

    private var endReached = false

    override suspend fun syncRepositories() {
        val repositories = githubService.getRepos()
        repositoriesDao.deleteAndInsertRepositories(
            repositoryMapper.mapToRepositoryDataModels(
                repositories
            )
        )
    }

    override suspend fun syncNextRepositories() {
        if (endReached) return
        val nextPage = getNextPage()
        val repositories = githubService.getRepos(nextPage)
        repositoriesDao.insertRepositories(
            repositoryMapper.mapToRepositoryDataModels(
                repositories
            )
        )
        if (repositories.size < ITEMS_PER_PAGE) endReached = true
    }

    override fun getRepositories(): Flow<List<Repository>> {
        return repositoriesDao.getRepositories()
            .map { repositoryMapper.mapToRepositoryModels(it) }
    }

    override fun getRepoDetails(githubId: Int): Flow<Repository> {
        return repositoriesDao.getRepoDetails(githubId)
            .map { repositoryMapper.mapToRepositoryModel(it) }
    }

    private suspend fun getNextPage(): Int {
        val itemsPerPage = ITEMS_PER_PAGE
        val totalItems = repositoriesDao.getTotalRepositories()
        return (totalItems / itemsPerPage) + 1
    }
}
package com.example.reposcount.presentation.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reposcount.domain.network.IsConnectedToInternet
import com.example.reposcount.domain.repos.GetRepositories
import com.example.reposcount.domain.repos.SyncNextRepos
import com.example.reposcount.domain.repos.SyncRepositories
import com.example.reposcount.presentation.di.Dispatcher
import com.example.reposcount.presentation.di.RSDispatchers.IO
import com.example.reposcount.presentation.repos.mapper.RepoUiMapper
import com.example.reposcount.presentation.repos.model.RepoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ReposViewModel @Inject constructor(
    private val syncRepositories: SyncRepositories,
    private val getRepositories: GetRepositories,
    private val syncNextRepos: SyncNextRepos,
    private val repoUiMapper: RepoUiMapper,
    private val isConnectedToInternet: IsConnectedToInternet,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepoUiState())
    val uiState: StateFlow<RepoUiState> = _uiState.asStateFlow()

    init {
        syncRepos()
        observeRepos()
    }

    private fun syncRepos() {
        isConnectedToInternet()
            .flatMapLatest {
                if (it) {
                    syncRepositories()
                } else {
                    flowOf(Unit)
                }
            }
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onEach { hideLoading() }
            .catch {
                val x = it
                hideLoading()

            }
            .launchIn(viewModelScope)
    }

    private fun hideLoading() {
        _uiState.update { it.copy(isLoading = false) }
    }

    private fun observeRepos() {
        getRepositories()
            .map { repoUiMapper.mapToUiModels(it) }
            .onEach { repos ->
                _uiState.update {
                    _uiState.value.copy(repoUiModel = repos)
                }
            }
            .catch {
                // TODO show error
            }
            .launchIn(viewModelScope)
    }

    fun syncMoreRepos() {
        if (!_uiState.value.isLoadingMore) {
            viewModelScope.launch(ioDispatcher) {
                _uiState.update { uiState.value.copy(isLoadingMore = true) }
                runCatching { syncNextRepos() }
                    .onSuccess { hideSyncMoreLoading() }
                    .onFailure { hideSyncMoreLoading() }

            }
        }
    }

    private fun hideSyncMoreLoading() {
        _uiState.update { uiState.value.copy(isLoadingMore = false) }
    }
}
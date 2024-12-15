package com.example.reposcount.presentation.repos.model

data class RepoUiState(
    val repoUiModel: List<RepoUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val isLoadingMore: Boolean = false,
)

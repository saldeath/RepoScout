package com.example.reposcount.presentation.repos.model

data class RepoUiState(
    val repoUiModel: List<RepoUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
)

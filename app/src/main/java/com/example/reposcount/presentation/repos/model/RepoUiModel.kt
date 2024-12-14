package com.example.reposcount.presentation.repos.model

data class RepoUiModel(
    val githubId: Int,
    val name: String,
    val avatarUrl: String,
    val visibility: String,
    val private: String
)

package com.example.reposcount.presentation.repodetails.model

class RepoDetailsUiModel(
    val githubId: Int,
    val description: String?,
    val fullName: String,
    val htmlUrl: String,
    val name: String,
    val avatarUrl: String,
    val visibility: String,
    val private: String
)
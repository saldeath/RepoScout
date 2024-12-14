package com.example.reposcount.domain.repos.model

/**
 * Entity describing a repository
 *
 * @param[githubId] The id from github
 * @param[description] A short description of the repository.
 * @param[fullName] The full name of the repository including the owner
 * @param[repositoryUrl] The url of the repository
 * @param[name] The name of the repository
 * @param[avatarUrl] The avatar URL of the user
 * @param[visibility] The visibility of the repository.
 * @param[private] Whether the repository is private.
 */
data class Repository(
    val githubId: Int,
    val description: String?,
    val fullName: String,
    val repositoryUrl: String,
    val name: String,
    val avatarUrl: String,
    val visibility: String,
    val private: Boolean
)

package com.example.reposcount.presentation.repodetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class RepoDetails(val githubId: Int)

fun NavController.navigateToRepositoryDetailsScreen(githubId: Int) =
    navigate(RepoDetails(githubId))

fun NavGraphBuilder.repositoryDetailsScreen(onBackClicked: () -> Unit) {
    composable<RepoDetails> {
        RepoDetailsRoute(onBackClicked)
    }
}
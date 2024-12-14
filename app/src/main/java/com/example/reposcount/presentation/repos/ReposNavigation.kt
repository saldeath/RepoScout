package com.example.reposcount.presentation.repos

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Repositories

fun NavGraphBuilder.repositoriesScreen(onRepoClicked: (id: Int) -> Unit) {
    composable<Repositories> {
        RepositoriesRoute(onRepoClicked)
    }
}
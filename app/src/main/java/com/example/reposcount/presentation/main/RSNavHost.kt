package com.example.reposcount.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.reposcount.presentation.repodetails.navigateToRepositoryDetailsScreen
import com.example.reposcount.presentation.repodetails.repositoryDetailsScreen
import com.example.reposcount.presentation.repos.Repositories
import com.example.reposcount.presentation.repos.repositoriesScreen

@Composable
fun RSNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Repositories,
    ) {
        repositoriesScreen(
            onRepoClicked = { navController.navigateToRepositoryDetailsScreen(it) }
        )

        repositoryDetailsScreen(
            onBackClicked = navController::popBackStack
        )
    }
}
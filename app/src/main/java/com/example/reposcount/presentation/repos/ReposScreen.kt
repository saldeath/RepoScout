package com.example.reposcount.presentation.repos

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.reposcount.R
import com.example.reposcount.presentation.repos.model.RepoUiModel
import com.example.reposcount.presentation.repos.model.RepoUiState
import com.example.reposcount.presentation.theme.RepoScountTheme

@Composable
fun RepositoriesRoute(
    onRepoClicked: (id: Int) -> Unit,
    reposViewModel: ReposViewModel = hiltViewModel()
) {

    val uiState by reposViewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    val endOfListReached by remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisibleItemIndex == uiState.repoUiModel.lastIndex
        }
    }

    LaunchedEffect(endOfListReached) {
        if (endOfListReached) {
            reposViewModel.syncMoreRepos()
        }
    }

    RepositoriesScreen(
        uiState = uiState,
        listState,
        onRepoClicked
    )
}

@Composable
fun RepositoriesScreen(
    uiState: RepoUiState,
    listState: LazyListState,
    onRepoClicked: (id: Int) -> Unit
) {
    if (uiState.isLoading) {
        Loading()
    } else {
        RepositoryList(listState, uiState, onRepoClicked)
    }

    val context = LocalContext.current
    LaunchedEffect(uiState.errorSyncingRepos) {
        if (uiState.errorSyncingRepos) {
            Toast.makeText(
                context,
                context.getString(R.string.repos_error_getting_repositories), Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(uiState.errorSyncingMoreRepos) {
        if (uiState.errorSyncingMoreRepos) {
            Toast.makeText(
                context,
                context.getString(R.string.repos_error_getting_more_repositories),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
private fun RepositoryList(
    listState: LazyListState,
    uiState: RepoUiState,
    onRepoClicked: (id: Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                items = uiState.repoUiModel,
                key = { it.githubId }
            ) {
                RepositoryCardItem(it, onRepoClicked)
            }
            if (uiState.isLoadingMore) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
fun RepositoryCardItem(
    repoUiModel: RepoUiModel,
    onRepoClicked: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onRepoClicked(repoUiModel.githubId) },
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = repoUiModel.avatarUrl,
                contentDescription = repoUiModel.name,
                modifier = Modifier.height(100.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = repoUiModel.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = repoUiModel.visibility,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = repoUiModel.private,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewReposScreen() {
    RepoScountTheme {
        RepositoriesScreen(
            uiState = RepoUiState(
                repoUiModel = List(10) {
                    RepoUiModel(it,"Github", "", "Visible", "Private")
                },
                isLoading = false,
                isLoadingMore = false,
            ),
            listState = rememberLazyListState(),
            onRepoClicked = {}
        )
    }
}

package com.example.reposcount.presentation.repodetails

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.reposcount.presentation.repodetails.model.RepoDetailsUiModel
import com.example.reposcount.presentation.theme.RepoScountTheme

@Composable
fun RepoDetailsRoute(
    onBackClicked: () -> Unit,
    repoDetailsViewModel: RepoDetailsViewModel = hiltViewModel()
) {
    val uiState by repoDetailsViewModel.uiState.collectAsStateWithLifecycle()

    RepoDetailsScreen(uiState, onBackClicked)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailsScreen(state: RepoDetailsUiModel?, onBackClicked: () -> Unit) {
    state?.let { uiState ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(uiState.name) },
                    navigationIcon = {
                        IconButton(onClick = onBackClicked) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                        }
                    },
                )
            },
            floatingActionButton = {
                val context = LocalContext.current
                FloatingActionButton(
                    modifier = Modifier.testTag("cta"),
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.htmlUrl))
                        context.startActivity(intent)
                    },
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Open Web",
                    )
                }
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = uiState.avatarUrl,
                    contentDescription = uiState.name,
                    modifier = Modifier
                        .height(140.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally),
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Full Name",
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = state.fullName,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = state.description.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Visibility",
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = state.visibility,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Private",
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = state.private,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewReposScreen() {
    RepoScountTheme {
        RepoDetailsScreen(
            state = (
                    RepoDetailsUiModel(
                        1,
                        "Cool app",
                        "fastApi",
                        "",
                        "fast",
                        "",
                        "Yes",
                        "No"
                    )),
            onBackClicked = {}
        )
    }
}

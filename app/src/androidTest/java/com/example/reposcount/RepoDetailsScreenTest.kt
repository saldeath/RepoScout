package com.example.reposcount

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.reposcount.presentation.repodetails.RepoDetailsScreen
import com.example.reposcount.presentation.repodetails.model.RepoDetailsUiModel
import com.example.reposcount.presentation.theme.RepoScountTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoDetailsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testRepoDetailsScreen() {
        composeTestRule.setContent {
            RepoScountTheme {
                RepoDetailsScreen(
                    state = RepoDetailsUiModel(
                        githubId = 1,
                        name = "Cool app",
                        fullName = "fastApi",
                        description = "A fast API",
                        avatarUrl = "",
                        visibility = "Public",
                        private = "No",
                        htmlUrl = ""
                    ),
                    onBackClicked = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Cool app").assertExists()
        composeTestRule.onNodeWithText("fastApi").assertExists()
        composeTestRule.onNodeWithText("A fast API").assertExists()
        composeTestRule.onNodeWithText("Public").assertExists()
        composeTestRule.onNodeWithText("No").assertExists()
        composeTestRule.onNodeWithTag("cta").assertExists()
        composeTestRule.onNodeWithContentDescription("backIcon").assertExists()
    }

    @Test
    fun testBackButtonClicked() {
        composeTestRule.setContent {
            RepoScountTheme {
                RepoDetailsScreen(
                    state = RepoDetailsUiModel(
                        githubId = 1,
                        name = "Cool app",
                        fullName = "fastApi",
                        description = "A fast API",
                        avatarUrl = "",
                        visibility = "Public",
                        private = "No",
                        htmlUrl = ""
                    ),
                    onBackClicked = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("backIcon").performClick()
    }
}

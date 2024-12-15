package com.example.reposcount.presentation.repodetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.reposcount.domain.repos.GetRepoDetails
import com.example.reposcount.presentation.repodetails.mapper.RepoDetailsMapper
import com.example.reposcount.presentation.repodetails.model.RepoDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val getRepoDetails: GetRepoDetails,
    private val savedStateHandle: SavedStateHandle,
    private val repoDetailsMapper: RepoDetailsMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow<RepoDetailsUiModel?>(null)
    val uiState: StateFlow<RepoDetailsUiModel?> = _uiState.asStateFlow()

    init {
        getRepoDetails()
    }

    private fun getRepoDetails() {
        val id = savedStateHandle.getStateFlow("githubId", 1).value
        getRepoDetails(id)
            .map { repoDetailsMapper.mapToRepoDetailsUiModel(it) }
            .onEach { repoDetailsModel ->
                _uiState.update { repoDetailsModel }
            }
            .launchIn(viewModelScope)
    }
}
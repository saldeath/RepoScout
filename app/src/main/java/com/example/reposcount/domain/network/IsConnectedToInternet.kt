package com.example.reposcount.domain.network

import com.example.reposcount.domain.network.data.ConnectivityService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsConnectedToInternet @Inject constructor(
    private val connectivityService: ConnectivityService
) {

    operator fun invoke(): Flow<Boolean> {
        return connectivityService.observeConnectedToInternet()
    }
}
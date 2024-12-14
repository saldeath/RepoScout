package com.example.reposcount.domain.network.data

import kotlinx.coroutines.flow.Flow

interface ConnectivityService {

    fun observeConnectedToInternet(): Flow<Boolean>
}
package com.example.reposcount.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import androidx.core.content.getSystemService
import com.example.reposcount.domain.network.data.ConnectivityService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ConnectivityServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ConnectivityService {

    override fun observeConnectedToInternet(): Flow<Boolean> {
        val connectivityManager = context.getSystemService<ConnectivityManager>()
            ?: return flowOf(false)

        return callbackFlow {
            val callback = object : NetworkCallback() {

                private val networks = mutableSetOf<Network>()

                override fun onAvailable(network: Network) {
                    networks += network
                    trySend(true)
                }

                override fun onLost(network: Network) {
                    networks -= network
                    trySend(networks.isNotEmpty())
                }
            }

            val request = Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            runCatching {
                connectivityManager.registerNetworkCallback(request, callback)
                trySend(connectivityManager.isCurrentlyConnected())
            }.onFailure {
                trySend(false)
                unregisterNetworkCallback(connectivityManager, callback)
            }

            awaitClose {
                unregisterNetworkCallback(connectivityManager, callback)
            }
        }
    }

    private fun unregisterNetworkCallback(
        connectivityManager: ConnectivityManager,
        callback: NetworkCallback
    ) {
        connectivityManager.unregisterNetworkCallback(callback)
    }

    private fun ConnectivityManager.isCurrentlyConnected() = activeNetwork
        ?.let(::getNetworkCapabilities)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
}
package com.numq.mvarchitecture.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

interface NetworkApi {

    val status: Flow<NetworkStatus>
    val isAvailable: Boolean

    class Implementation constructor(
        context: Context
    ) : NetworkApi {

        private val coroutineContext = Dispatchers.Default + Job()
        private val coroutineScope = CoroutineScope(coroutineContext)

        private val connectivityManager: ConnectivityManager? =
            context.getSystemService(ConnectivityManager::class.java)

        override val status = callbackFlow {
            val sendStatus: ProducerScope<NetworkStatus>.(NetworkStatus) -> Unit = {
                coroutineScope.launch {
                    send(it)
                }
            }
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    sendStatus(NetworkStatus.Available)
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    sendStatus(NetworkStatus.Unavailable)
                }

                override fun onLost(network: Network) {
                    sendStatus(NetworkStatus.Unavailable)
                }

                override fun onUnavailable() {
                    sendStatus(NetworkStatus.Unavailable)
                }
            }
            connectivityManager?.registerDefaultNetworkCallback(networkCallback)
            awaitClose {
                connectivityManager?.unregisterNetworkCallback(networkCallback)
            }
        }.distinctUntilChanged()

        override val isAvailable: Boolean =
            connectivityManager?.activeNetwork?.let { activeNetwork ->
                connectivityManager.getNetworkCapabilities(activeNetwork)?.run {
                    return@run when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            } ?: false
    }

}
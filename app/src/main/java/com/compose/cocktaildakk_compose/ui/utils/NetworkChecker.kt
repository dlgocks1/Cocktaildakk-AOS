package com.compose.cocktaildakk_compose.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.compose.cocktaildakk_compose.domain.model.NetworkState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NetworkChecker @Inject constructor(
    @ApplicationContext private val appContext: Context,
) {
    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.None)
    val networkState: StateFlow<NetworkState> get() = _networkState

    private val validTransportTypes = listOf(
        NetworkCapabilities.TRANSPORT_WIFI,
        NetworkCapabilities.TRANSPORT_CELLULAR,
    )

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _networkState.value = NetworkState.Connected
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _networkState.value = NetworkState.NotConnected
        }
    }

    private val connectivityManager: ConnectivityManager? =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    init {
        connectivityManager?.run {
            initiateNetworkState(this)
            registerNetworkCallback(this)
        }
    }

    private fun initiateNetworkState(manager: ConnectivityManager) {
        _networkState.value = manager.activeNetwork?.let {
            manager.getNetworkCapabilities(it)
        }?.let { networkCapabilities ->
            if (validTransportTypes.any { networkCapabilities.hasTransport(it) }) {
                NetworkState.Connected
            } else {
                NetworkState.NotConnected
            }
        } ?: NetworkState.NotConnected
    }

    private fun registerNetworkCallback(manager: ConnectivityManager) {
        NetworkRequest.Builder().apply {
            validTransportTypes.onEach { addTransportType(it) }
        }.let {
            manager.registerNetworkCallback(it.build(), networkCallback)
        }
    }
}

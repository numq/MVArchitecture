package com.numq.mvarchitecture.network

sealed interface NetworkStatus {
    object Available : NetworkStatus
    object Unavailable : NetworkStatus
}
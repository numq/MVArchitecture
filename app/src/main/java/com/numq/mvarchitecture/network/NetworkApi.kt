package com.numq.mvarchitecture.network

import kotlinx.coroutines.flow.Flow

interface NetworkApi {
    val state: Flow<NetworkStatus>
    val isAvailable: Boolean
}
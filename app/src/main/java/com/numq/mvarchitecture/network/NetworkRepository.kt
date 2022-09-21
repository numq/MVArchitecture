package com.numq.mvarchitecture.network

import arrow.core.Either
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    val state: Either<Exception, Flow<NetworkStatus>>
    val isAvailable: Either<Exception, Boolean>
}
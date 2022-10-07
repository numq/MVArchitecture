package com.numq.mvarchitecture.network

import arrow.core.Either
import arrow.core.right
import com.numq.mvarchitecture.wrapper.wrap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

interface NetworkRepository {

    val status: Either<Exception, Flow<NetworkStatus>>
    val isAvailable: Either<Exception, Boolean>

    class Implementation constructor(
        service: NetworkApi
    ) : NetworkRepository {
        override val status = service.status.wrap()
            .map {
                it.onStart {
                    emit(
                        if (service.isAvailable) NetworkStatus.Available else NetworkStatus.Unavailable
                    )
                }
            }
        override val isAvailable = service.isAvailable.right()
    }

}
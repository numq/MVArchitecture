package com.numq.mvarchitecture.network

import arrow.core.right

class NetworkData constructor(
    service: NetworkApi
) : NetworkRepository {
    override val state = service.state.right()
    override val isAvailable = service.isAvailable.right()
}
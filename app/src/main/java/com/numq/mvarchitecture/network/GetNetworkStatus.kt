package com.numq.mvarchitecture.network

import com.numq.mvarchitecture.interactor.UseCase
import kotlinx.coroutines.flow.Flow

class GetNetworkStatus constructor(
    private val repository: NetworkRepository
) : UseCase<Unit, Flow<NetworkStatus>>() {
    override suspend fun execute(arg: Unit) = repository.status
}
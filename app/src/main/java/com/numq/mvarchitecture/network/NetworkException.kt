package com.numq.mvarchitecture.network

sealed interface NetworkException {
    object Default : Exception("Unable to connect to internet"), NetworkException
}
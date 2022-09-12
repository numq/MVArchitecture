package com.numq.mvarchitecture.image

sealed class ImageException private constructor(
    override val message: String
) : Exception(message) {
    object Default : ImageException("Something get wrong")
    object UnavailableService : ImageException("Service is unavailable")
}

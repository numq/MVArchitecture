package com.numq.mvarchitecture.image

import android.util.Log
import arrow.core.*
import com.numq.mvarchitecture.network.NetworkException
import com.numq.mvarchitecture.network.NetworkHandler
import com.numq.mvarchitecture.wrapper.either
import com.numq.mvarchitecture.wrapper.eitherUri
import com.numq.mvarchitecture.wrapper.wrap

class ImageData constructor(
    private val networkHandler: NetworkHandler,
    private val service: ImageApi,
    private val dao: ImageDao
) : ImageRepository {
    override fun getRandomImage(size: ImageSize) = Either.conditionally(
        networkHandler.isConnected,
        ifFalse = { NetworkException.Default },
        ifTrue = {
            service.getRandomImage(size.height, size.width).eitherUri().map { uri ->
                when (val id = Regex("(?<=id/)(\\d*)").find(uri)) {
                    null -> ImageException.UnavailableService.left()
                    else -> dao.getById(id.value)?.right() ?: service.getImageDetails(id.value)
                        .either()
                }
            }.flatten().tap {
                Log.d(javaClass.simpleName, it.downloadUrl)
            }
        }
    ).flatten()

    override fun checkFavorite(id: String) =
        dao.getById(id).wrap().map { it != null }

    override fun getFavorites(skip: Int, limit: Int) =
        dao.getAll(skip = skip, limit = limit).wrap().leftIfNull { ImageException.Default }

    override fun addFavorite(image: Image) =
        dao.ensureInsert(image)?.copy(isFavorite = true).wrap()
            .leftIfNull { ImageException.Default }

    override fun removeFavorite(image: Image) =
        dao.ensureDelete(image)?.copy(isFavorite = false).wrap()
            .leftIfNull { ImageException.Default }
}
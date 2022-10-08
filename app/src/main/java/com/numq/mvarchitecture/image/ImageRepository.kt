package com.numq.mvarchitecture.image

import arrow.core.*
import com.numq.mvarchitecture.network.NetworkApi
import com.numq.mvarchitecture.network.NetworkException
import com.numq.mvarchitecture.wrapper.either
import com.numq.mvarchitecture.wrapper.eitherUri
import com.numq.mvarchitecture.wrapper.wrap
import com.numq.mvarchitecture.wrapper.wrapIf

interface ImageRepository {

    fun getRandomImage(size: ImageSize): Either<Exception, Image>
    fun getFavorites(skip: Int, limit: Int): Either<Exception, List<Image>>
    fun addFavorite(image: Image): Either<Exception, Image>
    fun removeFavorite(image: Image): Either<Exception, Image>
    fun checkFavorite(id: String): Either<Exception, Boolean>

    class Implementation constructor(
        private val networkService: NetworkApi,
        private val imageService: ImageApi,
        private val imageDao: ImageDao
    ) : ImageRepository {
        override fun getRandomImage(size: ImageSize) =
            imageService.getRandomImage(size.height, size.width).eitherUri().flatMap { uri ->
                when (val id = Regex("(?<=id/)(\\d*)").find(uri)) {
                    null -> ImageException.UnavailableService.left()
                    else -> imageDao.getById(id.value)?.wrap()
                        ?: imageService.getImageDetails(id.value)
                            .either()
                }
            }.wrapIf(networkService.isAvailable, NetworkException.Default).flatten()

        override fun checkFavorite(id: String) =
            imageDao.getById(id).wrap().map { it != null }

        override fun getFavorites(skip: Int, limit: Int) =
            imageDao.getAll(skip = skip, limit = limit).wrap().leftIfNull { ImageException.Default }

        override fun addFavorite(image: Image) =
            imageDao.ensureInsert(image)?.copy(isFavorite = true).wrap()
                .leftIfNull { ImageException.Default }

        override fun removeFavorite(image: Image) =
            imageDao.ensureDelete(image)?.copy(isFavorite = false).wrap()
                .leftIfNull { ImageException.Default }
    }

}
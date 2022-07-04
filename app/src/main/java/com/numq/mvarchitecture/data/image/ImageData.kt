package com.numq.mvarchitecture.data.image

import android.util.Log
import arrow.core.flatten
import arrow.core.left
import arrow.core.right
import arrow.core.rightIfNotNull
import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.entity.ImageSize
import com.numq.mvarchitecture.domain.repository.ImageRepository
import com.numq.mvarchitecture.platform.exception.AppExceptions
import com.numq.mvarchitecture.platform.extension.either
import com.numq.mvarchitecture.platform.extension.eitherUri

class ImageData constructor(
    private val service: ImageApi,
    private val dao: ImageDao
) : ImageRepository {
    override fun getRandomImage(size: ImageSize) =
        service.getRandomImage(size.height, size.width).eitherUri().map { uri ->
            when (val id = Regex("(?<=id/)(\\d*)").find(uri)) {
                null -> AppExceptions.Default.left()
                else -> dao.getById(id.value)?.right() ?: service.getImageDetails(id.value).either()
            }
        }.flatten().tap {
            Log.d(javaClass.simpleName, it.downloadUrl)
        }

    override fun checkFavorite(id: String) =
        (dao.getById(id) != null).rightIfNotNull { AppExceptions.Default }

    override fun getFavorites(skip: Int, limit: Int) =
        dao.getAll(skip = skip, limit = limit).rightIfNotNull { AppExceptions.Default }

    override fun addFavorite(image: Image) = dao.ensureInsert(image)?.copy(isFavorite = true)
        .rightIfNotNull { AppExceptions.Default }

    override fun removeFavorite(image: Image) = dao.ensureDelete(image)?.copy(isFavorite = false)
        .rightIfNotNull { AppExceptions.Default }
}
package com.numq.mvarchitecture.domain.repository

import arrow.core.Either
import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.entity.ImageSize

interface ImageRepository {
    fun getRandomImage(size: ImageSize): Either<Exception, Image>
    fun getFavorites(skip: Int, limit: Int): Either<Exception, List<Image>>
    fun addFavorite(image: Image): Either<Exception, Image>
    fun removeFavorite(image: Image): Either<Exception, Image>
    fun checkFavorite(id: String): Either<Exception, Boolean>
}
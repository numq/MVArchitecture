package com.numq.mvarchitecture.usecase

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.repository.ImageRepository

class RemoveFavorite
constructor(private val repository: ImageRepository) : UseCase<Image, Image>() {
    override fun execute(arg: Image) = repository.removeFavorite(arg)
}
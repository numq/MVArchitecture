package com.numq.mvarchitecture.usecase

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.entity.ImageSize
import com.numq.mvarchitecture.domain.repository.ImageRepository

class GetRandomImage
constructor(private val data: ImageRepository) : UseCase<ImageSize, Image>() {
    override fun execute(arg: ImageSize) = data.getRandomImage(arg)
}
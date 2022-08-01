package com.numq.mvarchitecture.image

import com.numq.mvarchitecture.interactor.UseCase

class GetRandomImage
constructor(private val data: ImageRepository) : UseCase<ImageSize, Image>() {
    override fun execute(arg: ImageSize) = data.getRandomImage(arg)
}
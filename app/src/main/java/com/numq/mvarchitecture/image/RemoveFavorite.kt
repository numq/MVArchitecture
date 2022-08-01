package com.numq.mvarchitecture.image

import com.numq.mvarchitecture.interactor.UseCase

class RemoveFavorite
constructor(private val repository: ImageRepository) : UseCase<Image, Image>() {
    override fun execute(arg: Image) = repository.removeFavorite(arg)
}
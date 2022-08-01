package com.numq.mvarchitecture.image

import com.numq.mvarchitecture.interactor.UseCase

class CheckFavorite
constructor(private val repository: ImageRepository) : UseCase<String, Boolean>() {
    override fun execute(arg: String) = repository.checkFavorite(arg)
}
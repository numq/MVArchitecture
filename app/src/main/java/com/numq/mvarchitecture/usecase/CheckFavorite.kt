package com.numq.mvarchitecture.usecase

import com.numq.mvarchitecture.domain.repository.ImageRepository

class CheckFavorite
constructor(private val repository: ImageRepository) : UseCase<String, Boolean>() {
    override fun execute(arg: String) = repository.checkFavorite(arg)
}
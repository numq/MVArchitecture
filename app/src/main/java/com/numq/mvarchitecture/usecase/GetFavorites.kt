package com.numq.mvarchitecture.usecase

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.repository.ImageRepository

class GetFavorites
constructor(private val data: ImageRepository) : UseCase<Pair<Int, Int>, List<Image>>() {
    override fun execute(arg: Pair<Int, Int>) =
        data.getFavorites(arg.first, arg.second)
}
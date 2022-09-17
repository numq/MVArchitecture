package com.numq.mvarchitecture.image

import com.numq.mvarchitecture.interactor.UseCase

class GetFavorites
constructor(private val data: ImageRepository) : UseCase<Pair<Int, Int>, List<Image>>() {
    override suspend fun execute(arg: Pair<Int, Int>) =
        data.getFavorites(arg.first, arg.second)
}
package com.numq.mvarchitecture.image

import retrofit2.Retrofit

class ImageService
constructor(private val retrofit: Retrofit) : ImageApi {

    override fun getRandomImage(height: Int, width: Int) =
        retrofit.create(ImageApi::class.java).getRandomImage(width, height)

    override fun getImageDetails(id: String) =
        retrofit.create(ImageApi::class.java).getImageDetails(id)
}
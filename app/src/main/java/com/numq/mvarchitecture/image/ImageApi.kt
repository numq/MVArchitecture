package com.numq.mvarchitecture.image

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApi {

    companion object {
        const val BASE_URL = "https://picsum.photos"
        const val RANDOM_IMAGE = "/{width}/{height}"
        const val IMAGE_DETAILS = "/id/{id}/info"
    }

    @GET(RANDOM_IMAGE)
    fun getRandomImage(
        @Path("height") height: Int,
        @Path("width") width: Int
    ): Call<ResponseBody>

    @GET(IMAGE_DETAILS)
    fun getImageDetails(
        @Path("id") id: String
    ): Call<Image>

    class Implementation constructor(
        private val retrofit: Retrofit
    ) : ImageApi {

        override fun getRandomImage(height: Int, width: Int) =
            retrofit.create(ImageApi::class.java).getRandomImage(width, height)

        override fun getImageDetails(id: String) =
            retrofit.create(ImageApi::class.java).getImageDetails(id)
    }

}
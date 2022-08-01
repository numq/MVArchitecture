package com.numq.mvarchitecture.image

import com.numq.mvarchitecture.constant.AppConstants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApi {
    @GET(AppConstants.Api.Image.RANDOM_IMAGE)
    fun getRandomImage(
        @Path("height") height: Int,
        @Path("width") width: Int
    ): Call<ResponseBody>

    @GET(AppConstants.Api.Image.IMAGE_DETAILS)
    fun getImageDetails(
        @Path("id") id: String
    ): Call<Image>
}
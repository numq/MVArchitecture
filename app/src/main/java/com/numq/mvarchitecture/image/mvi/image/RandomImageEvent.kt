package com.numq.mvarchitecture.image.mvi.image

import com.numq.mvarchitecture.image.Image
import com.numq.mvarchitecture.image.ImageSize
import com.numq.mvarchitecture.image.mvi.base.Feature

interface RandomImageEvent : Feature.Event {
    data class GetRandomImage(val size: ImageSize) : RandomImageEvent
    data class UpdateImage(val id: String) : RandomImageEvent
    data class AddFavorite(val image: Image) : RandomImageEvent
    data class RemoveFavorite(val image: Image) : RandomImageEvent
}
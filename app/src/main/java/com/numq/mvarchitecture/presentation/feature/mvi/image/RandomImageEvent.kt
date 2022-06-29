package com.numq.mvarchitecture.presentation.feature.mvi.image

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.entity.ImageSize
import com.numq.mvarchitecture.presentation.feature.mvi.base.Feature

interface RandomImageEvent : Feature.Event {
    data class GetRandomImage(val size: ImageSize) : RandomImageEvent
    data class UpdateImage(val id: String) : RandomImageEvent
    data class AddFavorite(val image: Image) : RandomImageEvent
    data class RemoveFavorite(val image: Image) : RandomImageEvent
}
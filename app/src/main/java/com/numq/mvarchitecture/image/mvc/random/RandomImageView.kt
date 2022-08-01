package com.numq.mvarchitecture.image.mvc.random

import com.numq.mvarchitecture.image.Image
import com.numq.mvarchitecture.image.mvc.base.View
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RandomImageView : View.RandomImage {

    override val error: MutableSharedFlow<Exception> = MutableSharedFlow()

    private val _randomImage = MutableStateFlow<Image?>(null)
    override val randomImage = _randomImage.asStateFlow()

    override fun onImage(image: Image?) {
        _randomImage.update { image }
    }

    override fun onImageState(state: Boolean) {
        _randomImage.update { it?.copy(isFavorite = state) }
    }
}
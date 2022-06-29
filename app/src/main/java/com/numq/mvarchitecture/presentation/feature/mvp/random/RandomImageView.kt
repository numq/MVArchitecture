package com.numq.mvarchitecture.presentation.feature.mvp.random

import com.numq.mvarchitecture.domain.entity.Image
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RandomImageView : RandomImageContract.View {

    override val error: MutableSharedFlow<Exception> = MutableSharedFlow()

    private val _randomImage = MutableStateFlow<Image?>(null)
    override val randomImage = _randomImage.asStateFlow()

    override fun onImage(image: Image) {
        _randomImage.update { image }
    }

    override fun onImageState(state: Boolean) {
        _randomImage.update { it?.copy(isFavorite = state) }
    }
}
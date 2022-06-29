package com.numq.mvarchitecture.presentation.feature.mvp.random

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.entity.ImageSize
import com.numq.mvarchitecture.presentation.feature.mvp.base.BasePresenter
import com.numq.mvarchitecture.presentation.feature.mvp.base.BaseView
import kotlinx.coroutines.flow.StateFlow

interface RandomImageContract {

    interface View : BaseView {
        val randomImage: StateFlow<Image?>
        fun onImage(image: Image)
        fun onImageState(state: Boolean)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun updateImage(id: String)
        abstract fun randomImage(size: ImageSize)
        abstract fun addFavorite(image: Image)
        abstract fun removeFavorite(image: Image)
    }
}
package com.numq.mvarchitecture.image.mvp.random

import com.numq.mvarchitecture.image.Image
import com.numq.mvarchitecture.image.ImageSize
import com.numq.mvarchitecture.image.mvp.base.BasePresenter
import com.numq.mvarchitecture.image.mvp.base.BaseView
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
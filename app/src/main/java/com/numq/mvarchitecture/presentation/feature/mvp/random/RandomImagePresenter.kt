package com.numq.mvarchitecture.presentation.feature.mvp.random

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.entity.ImageSize
import com.numq.mvarchitecture.usecase.AddFavorite
import com.numq.mvarchitecture.usecase.CheckFavorite
import com.numq.mvarchitecture.usecase.GetRandomImage
import com.numq.mvarchitecture.usecase.RemoveFavorite

class RandomImagePresenter constructor(
    private val checkFavorite: CheckFavorite,
    private val getRandomImage: GetRandomImage,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite
) : RandomImageContract.Presenter() {

    override fun randomImage(size: ImageSize) {
        display {
            getRandomImage.invoke(size) {
                it.fold(::onError, ::onImage)
            }
        }
    }

    override fun updateImage(id: String) {
        display {
            checkFavorite.invoke(id) {
                it.fold(::onError, ::onImageState)
            }
        }
    }

    override fun addFavorite(image: Image) {
        display {
            addFavorite.invoke(image) {
                it.fold(::onError, ::onImage)
            }
        }
    }

    override fun removeFavorite(image: Image) {
        display {
            removeFavorite.invoke(image) {
                it.fold(::onError, ::onImage)
            }
        }
    }
}
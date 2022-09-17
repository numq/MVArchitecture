package com.numq.mvarchitecture.image.mvp.random

import com.numq.mvarchitecture.image.*

class RandomImagePresenter constructor(
    private val checkFavorite: CheckFavorite,
    private val getRandomImage: GetRandomImage,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite
) : RandomImageContract.Presenter() {

    override fun randomImage(size: ImageSize) {
        display {
            getRandomImage.invoke(size, ::onError, ::onImage)
        }
    }

    override fun updateImage(id: String) {
        display {
            checkFavorite.invoke(id, ::onError, ::onImageState)
        }
    }

    override fun addFavorite(image: Image) {
        display {
            addFavorite.invoke(image, ::onError, ::onImage)
        }
    }

    override fun removeFavorite(image: Image) {
        display {
            removeFavorite.invoke(image, ::onError, ::onImage)
        }
    }

}
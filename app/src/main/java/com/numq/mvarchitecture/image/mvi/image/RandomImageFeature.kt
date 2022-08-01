package com.numq.mvarchitecture.image.mvi.image

import com.numq.mvarchitecture.image.AddFavorite
import com.numq.mvarchitecture.image.CheckFavorite
import com.numq.mvarchitecture.image.GetRandomImage
import com.numq.mvarchitecture.image.RemoveFavorite
import com.numq.mvarchitecture.image.mvi.base.Feature

class RandomImageFeature constructor(
    private val checkFavorite: CheckFavorite,
    private val getRandomImage: GetRandomImage,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite
) : Feature<RandomImageState, RandomImageEvent, RandomImageEffect>(RandomImageState()) {

    private val onError: (Exception) -> Unit = { e ->
        reveal(RandomImageEffect.ShowError(e))
    }

    override fun handleEvent(event: RandomImageEvent) = when (event) {
        is RandomImageEvent.GetRandomImage -> {
            getRandomImage.invoke(event.size) {
                it.fold(onError) { image ->
                    reduce { oldState ->
                        oldState.copy(randomImage = image)
                    }
                }
            }
        }
        is RandomImageEvent.UpdateImage -> {
            checkFavorite.invoke(event.id) {
                it.fold(onError) { state ->
                    reduce { oldState ->
                        oldState.copy(randomImage = oldState.randomImage?.copy(isFavorite = state))
                    }
                }
            }
        }
        is RandomImageEvent.AddFavorite -> {
            addFavorite.invoke(event.image) {
                it.fold(onError) { image ->
                    reduce { oldState ->
                        oldState.copy(randomImage = image)
                    }
                }
            }
        }
        is RandomImageEvent.RemoveFavorite -> {
            removeFavorite.invoke(event.image) {
                it.fold(onError) { image ->
                    reduce { oldState ->
                        oldState.copy(randomImage = image)
                    }
                }
            }
        }
        else -> Unit
    }

}
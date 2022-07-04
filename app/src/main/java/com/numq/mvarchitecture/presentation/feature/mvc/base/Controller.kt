package com.numq.mvarchitecture.presentation.feature.mvc.base

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.entity.ImageSize
import com.numq.mvarchitecture.usecase.*

class Controller constructor(
    private val imageView: View.RandomImage,
    private val favoritesView: View.Favorites,
    private val checkFavorite: CheckFavorite,
    private val getRandomImage: GetRandomImage,
    private val getFavorites: GetFavorites,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite
) {

    fun randomImage(size: ImageSize) = getRandomImage.invoke(size) {
        it.fold(imageView::onError, imageView::onImage)
    }

    fun updateImage(id: String) = checkFavorite.invoke(id) {
        it.fold(imageView::onError, imageView::onImageState)
    }

    fun addFavorite(image: Image) = addFavorite.invoke(image) {
        it.fold(imageView::onError, imageView::onImage)
    }

    fun removeFavorite(image: Image) = removeFavorite.invoke(image) {
        it.fold(imageView::onError, imageView::onImage)
    }

    fun loadMore(skip: Int, limit: Int) = getFavorites.invoke(Pair(skip, limit)) {
        it.fold(favoritesView::onError, favoritesView::onFavorites)
    }

    fun removeFromFavorites(image: Image) = removeFavorite.invoke(image) {
        it.fold(favoritesView::onError, favoritesView::onRemoveFavorite)
    }

    fun undoRemoval(image: Image) = addFavorite.invoke(image) {
        it.fold(favoritesView::onError, favoritesView::onUndoRemoval)
    }

}
package com.numq.mvarchitecture.image.mvc.base

import com.numq.mvarchitecture.image.*

class Controller constructor(
    private val imageView: View.RandomImage,
    private val favoritesView: View.Favorites,
    private val checkFavorite: CheckFavorite,
    private val getRandomImage: GetRandomImage,
    private val getFavorites: GetFavorites,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite
) {

    fun randomImage(size: ImageSize) =
        getRandomImage.invoke(size, imageView::onError, imageView::onImage)

    fun updateImage(id: String) =
        checkFavorite.invoke(id, imageView::onError, imageView::onImageState)

    fun addFavorite(image: Image) =
        addFavorite.invoke(image, imageView::onError, imageView::onImage)

    fun removeFavorite(image: Image) =
        removeFavorite.invoke(image, imageView::onError, imageView::onImage)

    fun loadMore(skip: Int, limit: Int) =
        getFavorites.invoke(Pair(skip, limit), favoritesView::onError, favoritesView::onFavorites)

    fun removeFromFavorites(image: Image) =
        removeFavorite.invoke(image, favoritesView::onError, favoritesView::onRemoveFavorite)

    fun undoRemoval(image: Image) =
        addFavorite.invoke(image, favoritesView::onError, favoritesView::onUndoRemoval)

}
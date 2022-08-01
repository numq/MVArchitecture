package com.numq.mvarchitecture.image.mvp.favorites

import com.numq.mvarchitecture.image.Image
import com.numq.mvarchitecture.image.mvp.base.BasePresenter
import com.numq.mvarchitecture.image.mvp.base.BaseView
import kotlinx.coroutines.flow.StateFlow

interface FavoritesContract {

    interface View : BaseView {
        var indexToRemove: Int?
        val favorites: StateFlow<List<Image>>
        fun onFavorites(images: List<Image>)
        fun onRemoveFavorite(image: Image)
        fun onUndoRemoval(image: Image)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun loadMore(skip: Int, limit: Int)
        abstract fun removeFavorite(image: Image)
        abstract fun undoRemoval(image: Image)
    }
}
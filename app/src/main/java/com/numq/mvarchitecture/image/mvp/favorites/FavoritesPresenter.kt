package com.numq.mvarchitecture.image.mvp.favorites

import com.numq.mvarchitecture.image.AddFavorite
import com.numq.mvarchitecture.image.GetFavorites
import com.numq.mvarchitecture.image.Image
import com.numq.mvarchitecture.image.RemoveFavorite

class FavoritesPresenter constructor(
    private val getFavorites: GetFavorites,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite,
) : FavoritesContract.Presenter() {

    override fun loadMore(skip: Int, limit: Int) {
        display {
            getFavorites.invoke(Pair(skip, limit), ::onError, ::onFavorites)
        }
    }

    override fun removeFavorite(image: Image) {
        display {
            removeFavorite.invoke(image, ::onError, ::onRemoveFavorite)
        }
    }

    override fun undoRemoval(image: Image) {
        display {
            addFavorite.invoke(image, ::onError, ::onUndoRemoval)
        }
    }

}
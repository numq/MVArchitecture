package com.numq.mvarchitecture.presentation.feature.mvp.favorites

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.usecase.AddFavorite
import com.numq.mvarchitecture.usecase.GetFavorites
import com.numq.mvarchitecture.usecase.RemoveFavorite

class FavoritesPresenter constructor(
    private val getFavorites: GetFavorites,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite,
) : FavoritesContract.Presenter() {

    override fun loadMore(skip: Int, limit: Int) {
        display {
            getFavorites.invoke(Pair(skip, limit)) {
                it.fold(::onError, ::onFavorites)
            }
        }
    }

    override fun removeFavorite(image: Image) {
        display {
            removeFavorite.invoke(image) {
                it.fold(::onError, ::onRemoveFavorite)
            }
        }
    }

    override fun undoRemoval(image: Image) {
        display {
            addFavorite.invoke(image) {
                it.fold(::onError, ::onUndoRemoval)
            }
        }
    }
}
package com.numq.mvarchitecture.presentation.feature.mvi.favorites

import com.numq.mvarchitecture.presentation.feature.mvi.base.Feature
import com.numq.mvarchitecture.usecase.AddFavorite
import com.numq.mvarchitecture.usecase.GetFavorites
import com.numq.mvarchitecture.usecase.RemoveFavorite

class FavoritesFeature constructor(
    private val getFavorites: GetFavorites,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite,
) : Feature<FavoritesState, FavoritesEvent, FavoritesEffect>(FavoritesState()) {

    private val onError: (Exception) -> Unit = { e ->
        reveal(FavoritesEffect.ShowError(e))
    }

    private var indexToRemove: Int? = null

    fun getFavorites(skip: Int, limit: Int) = dispatch(FavoritesEvent.LoadMore(skip, limit))

    override fun handleEvent(event: FavoritesEvent) = when (event) {
        is FavoritesEvent.LoadMore -> {
            getFavorites.invoke(Pair(event.skip, event.limit)) {
                it.fold(onError) { list ->
                    reduce { oldState ->
                        oldState.copy(favorites = list)
                    }
                }
            }
        }
        is FavoritesEvent.RemoveFavorite -> {
            removeFavorite.invoke(event.image) {
                it.fold(onError) { image ->
                    reduce { oldState ->
                        indexToRemove =
                            oldState.favorites.indexOfFirst { img -> img.id == image.id }
                        oldState.copy(favorites = oldState.favorites.filter { img -> img.id != image.id })
                    }
                }
            }
        }
        is FavoritesEvent.UndoRemoval -> {
            addFavorite.invoke(event.image) {
                it.fold(onError) { image ->
                    indexToRemove?.let { idx ->
                        reduce { oldState ->
                            oldState.copy(
                                favorites = oldState.favorites.subList(0, idx).plus(image)
                                    .plus(oldState.favorites.subList(idx, oldState.favorites.size))
                            )
                        }
                        indexToRemove = null
                    }
                }
            }
        }
        else -> Unit
    }
}
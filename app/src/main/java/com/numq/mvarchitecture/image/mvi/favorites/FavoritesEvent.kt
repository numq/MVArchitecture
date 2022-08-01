package com.numq.mvarchitecture.image.mvi.favorites

import com.numq.mvarchitecture.image.Image
import com.numq.mvarchitecture.image.mvi.base.Feature

interface FavoritesEvent : Feature.Event {
    data class LoadMore(val skip: Int, val limit: Int) : FavoritesEvent
    data class RemoveFavorite(val image: Image) : FavoritesEvent
    data class UndoRemoval(val image: Image) : FavoritesEvent
}
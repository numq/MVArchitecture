package com.numq.mvarchitecture.presentation.feature.mvi.favorites

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.presentation.feature.mvi.base.Feature

interface FavoritesEvent : Feature.Event {
    data class LoadMore(val skip: Int, val limit: Int) : FavoritesEvent
    data class RemoveFavorite(val image: Image) : FavoritesEvent
    data class UndoRemoval(val image: Image) : FavoritesEvent
}
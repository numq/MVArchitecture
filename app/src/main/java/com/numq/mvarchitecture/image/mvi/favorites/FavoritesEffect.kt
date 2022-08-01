package com.numq.mvarchitecture.image.mvi.favorites

import com.numq.mvarchitecture.image.mvi.base.Feature

interface FavoritesEffect : Feature.Effect {
    data class ShowError(val exception: Exception) : FavoritesEffect
}
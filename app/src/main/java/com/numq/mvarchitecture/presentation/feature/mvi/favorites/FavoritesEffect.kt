package com.numq.mvarchitecture.presentation.feature.mvi.favorites

import com.numq.mvarchitecture.presentation.feature.mvi.base.Feature

interface FavoritesEffect : Feature.Effect {
    data class ShowError(val exception: Exception) : FavoritesEffect
}
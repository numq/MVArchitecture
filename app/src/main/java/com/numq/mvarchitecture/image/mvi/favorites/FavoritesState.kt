package com.numq.mvarchitecture.image.mvi.favorites

import com.numq.mvarchitecture.image.Image
import com.numq.mvarchitecture.image.mvi.base.Feature

data class FavoritesState(val favorites: List<Image> = emptyList()) : Feature.State
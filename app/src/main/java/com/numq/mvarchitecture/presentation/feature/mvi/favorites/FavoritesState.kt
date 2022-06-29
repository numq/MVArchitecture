package com.numq.mvarchitecture.presentation.feature.mvi.favorites

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.presentation.feature.mvi.base.Feature

data class FavoritesState(val favorites: List<Image> = emptyList()) : Feature.State
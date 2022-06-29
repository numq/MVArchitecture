package com.numq.mvarchitecture.presentation.feature.mvi.image

import com.numq.mvarchitecture.presentation.feature.mvi.base.Feature

interface RandomImageEffect : Feature.Effect {
    data class ShowError(val exception: Exception) : RandomImageEffect
}
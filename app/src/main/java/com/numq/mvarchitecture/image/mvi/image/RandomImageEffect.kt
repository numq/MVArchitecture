package com.numq.mvarchitecture.image.mvi.image

import com.numq.mvarchitecture.image.mvi.base.Feature

interface RandomImageEffect : Feature.Effect {
    data class ShowError(val exception: Exception) : RandomImageEffect
}
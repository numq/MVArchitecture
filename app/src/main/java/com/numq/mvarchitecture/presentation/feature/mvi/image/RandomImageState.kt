package com.numq.mvarchitecture.presentation.feature.mvi.image

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.presentation.feature.mvi.base.Feature

data class RandomImageState(val randomImage: Image? = null) : Feature.State
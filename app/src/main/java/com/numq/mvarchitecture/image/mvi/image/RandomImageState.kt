package com.numq.mvarchitecture.image.mvi.image

import com.numq.mvarchitecture.image.Image
import com.numq.mvarchitecture.image.mvi.base.Feature

data class RandomImageState(val randomImage: Image? = null) : Feature.State
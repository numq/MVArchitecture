package com.numq.mvarchitecture.wrapper

import arrow.core.left
import arrow.core.right

fun <T> T.wrap() = runCatching { this }.fold({ it.right() },
    { Exception(it.localizedMessage ?: it.javaClass.simpleName).left() })
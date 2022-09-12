package com.numq.mvarchitecture.wrapper

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import retrofit2.Call

fun <T> Call<T>.either(): Either<Exception, T> =
    runCatching { execute().body() }.fold(
        { value -> value?.right() },
        { Exception(it.localizedMessage ?: it.javaClass.simpleName).left() }
    ) ?: Exception("Something get wrong").left()

fun <T> Call<T>.eitherUri(): Either<Exception, String> =
    runCatching { execute().raw().request.url.toString() }.fold(
        { value -> value.right() },
        { Exception(it.localizedMessage ?: it.javaClass.simpleName).left() }
    )
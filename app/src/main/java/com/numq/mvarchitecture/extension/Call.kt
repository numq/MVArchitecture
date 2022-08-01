package com.numq.mvarchitecture.extension

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.numq.mvarchitecture.error.AppExceptions
import retrofit2.Call

fun <T> Call<T>.either(): Either<Exception, T> =
    runCatching { execute().body() }.fold({ value ->
        value?.right() ?: AppExceptions.Default.left()
    }, { AppExceptions.NetworkException.left() })

fun <T> Call<T>.eitherUri(): Either<Exception, String> =
    runCatching { execute().raw().request.url.toString() }.fold({ value -> value.right() },
        { AppExceptions.NetworkException.left() })
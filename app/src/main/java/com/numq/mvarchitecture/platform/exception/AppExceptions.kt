package com.numq.mvarchitecture.platform.exception

object AppExceptions {
    object Default : Exception("Something get wrong")
    object NetworkException : Exception("Check your network connection")
}
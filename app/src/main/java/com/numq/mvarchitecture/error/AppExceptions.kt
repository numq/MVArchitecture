package com.numq.mvarchitecture.error

object AppExceptions {
    object Default : Exception("Something get wrong")
    object NetworkException : Exception("Check your network connection")
}
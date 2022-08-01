package com.numq.mvarchitecture.navigation

import androidx.navigation.NamedNavArgument

sealed class Route private constructor(
    val name: String,
    val destination: String = name.lowercase(),
    val args: List<NamedNavArgument> = emptyList()
) {

    private companion object {
        const val MVC = "MVC"
        const val MVP = "MVP"
        const val MVVM = "MVVM"
        const val MVI = "MVI"
    }

    object Mvc : Route(MVC)
    object Mvp : Route(MVP)
    object Mvvm : Route(MVVM)
    object Mvi : Route(MVI)
}
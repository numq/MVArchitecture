package com.numq.mvarchitecture.platform.navigation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
fun BottomNavigation(
    navController: NavController,
    backStackEntry: NavBackStackEntry?
) {
    val routes = arrayOf(Route.Mvc, Route.Mvp, Route.Mvvm, Route.Mvi)
    BottomAppBar {
        routes.forEach {
            val isSelected = backStackEntry?.destination?.route == it.destination
            BottomNavigationItem(
                icon = { if (isSelected) Icon(Icons.Rounded.Check, "") },
                label = {
                    Text(text = it.name)
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(it.destination) {
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}
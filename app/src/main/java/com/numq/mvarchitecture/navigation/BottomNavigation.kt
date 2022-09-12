package com.numq.mvarchitecture.navigation

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
                    if (!isSelected) {
                        navController.navigate(it.destination) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    }
                })
        }
    }
}
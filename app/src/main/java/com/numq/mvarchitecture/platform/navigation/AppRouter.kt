package com.numq.mvarchitecture.platform.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.numq.mvarchitecture.platform.permission.PermissionsRequired
import com.numq.mvarchitecture.presentation.feature.mvc.MvcScreen
import com.numq.mvarchitecture.presentation.feature.mvi.MviScreen
import com.numq.mvarchitecture.presentation.feature.mvp.MvpScreen
import com.numq.mvarchitecture.presentation.feature.mvvm.MvvmScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AppRouter() {

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val permissions = listOf(
        android.Manifest.permission.ACCESS_NETWORK_STATE,
        android.Manifest.permission.INTERNET
    )

    PermissionsRequired(permissions) {
        Scaffold(scaffoldState = scaffoldState, bottomBar = {
            BottomNavigation(navController, navBackStackEntry)
        }) {
            Box(
                Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                NavHost(
                    navController,
                    startDestination = Route.Mvc.destination
                ) {
                    composable(Route.Mvc.destination) {
                        MvcScreen(Route.Mvc, scaffoldState)
                    }
                    composable(Route.Mvp.destination) {
                        MvpScreen(Route.Mvp, scaffoldState)
                    }
                    composable(Route.Mvvm.destination) {
                        MvvmScreen(Route.Mvvm, scaffoldState)
                    }
                    composable(Route.Mvi.destination) {
                        MviScreen(Route.Mvi, scaffoldState)
                    }
                }
            }
        }
    }
}
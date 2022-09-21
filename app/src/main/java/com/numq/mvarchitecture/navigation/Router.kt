package com.numq.mvarchitecture.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.numq.mvarchitecture.image.mvc.MvcScreen
import com.numq.mvarchitecture.image.mvi.MviScreen
import com.numq.mvarchitecture.image.mvp.MvpScreen
import com.numq.mvarchitecture.image.mvvm.MvvmScreen
import com.numq.mvarchitecture.network.GetNetworkStatus
import com.numq.mvarchitecture.network.NetworkStatus
import com.numq.mvarchitecture.network.NetworkStatusNotification
import com.numq.mvarchitecture.permission.PermissionsRequired
import kotlinx.coroutines.launch
import org.koin.androidx.compose.inject

@Composable
fun AppRouter() {

    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val permissions = listOf(
        android.Manifest.permission.ACCESS_NETWORK_STATE,
        android.Manifest.permission.INTERNET
    )

    PermissionsRequired(permissions) {

        val getNetworkStatus: GetNetworkStatus by inject()

        val (networkStatus, setNetworkStatus) = remember {
            mutableStateOf<NetworkStatus?>(null)
        }

        LaunchedEffect(Unit) {
            getNetworkStatus.invoke(Unit, onException = {
                Log.e(javaClass.simpleName, it.localizedMessage ?: it.javaClass.simpleName)
            }, onResult = {
                coroutineScope.launch {
                    it.collect { status ->
                        setNetworkStatus(status)
                    }
                }
            })
        }

        Scaffold(scaffoldState = scaffoldState, bottomBar = {
            BottomNavigation(navController, navBackStackEntry)
        }) {
            Box(
                Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                NavHost(
                    navController,
                    startDestination = Route.Mvc.destination,
                    modifier = Modifier.fillMaxSize()
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
                networkStatus?.let { status ->
                    NetworkStatusNotification(status) {
                        setNetworkStatus(null)
                    }
                }
            }
        }
    }
}
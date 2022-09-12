package com.numq.mvarchitecture.error

import androidx.activity.compose.BackHandler
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ErrorMessage(
    exception: Exception,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onErrorShown: () -> Unit = {}
) {
    BackHandler(scaffoldState.snackbarHostState.currentSnackbarData != null) {
        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
    }
    DisposableEffect(exception) {
        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                exception.localizedMessage ?: exception.javaClass.simpleName
            )
        }
        onDispose {
            onErrorShown()
        }
    }
}
package com.numq.mvarchitecture.image.mvp.base

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

interface BaseView {

    private val coroutineScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main + Job())

    val error: MutableSharedFlow<Exception>
    fun onError(e: Exception) {
        e.localizedMessage?.let { Log.e(javaClass.simpleName, it) }
        coroutineScope.launch {
            error.emit(e)
        }
    }

}

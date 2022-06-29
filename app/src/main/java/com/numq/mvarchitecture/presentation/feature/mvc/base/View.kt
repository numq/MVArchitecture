package com.numq.mvarchitecture.presentation.feature.mvc.base

import android.util.Log
import com.numq.mvarchitecture.domain.entity.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface View {

    private val coroutineScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main + Job())

    val error: MutableSharedFlow<Exception>
    fun onError(e: Exception) {
        e.localizedMessage?.let { Log.e(javaClass.simpleName, it) }
        coroutineScope.launch {
            error.emit(e)
        }
    }

    interface RandomImage : View {
        val randomImage: StateFlow<Image?>
        fun onImage(image: Image?)
        fun onImageState(state: Boolean)
    }

    interface Favorites : View {
        var indexToRemove: Int?
        val favorites: StateFlow<List<Image>>
        fun onFavorites(images: List<Image>)
        fun onRemoveFavorite(image: Image)
        fun onUndoRemoval(image: Image)
    }

}
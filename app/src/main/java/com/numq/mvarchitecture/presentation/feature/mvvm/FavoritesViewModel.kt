package com.numq.mvarchitecture.presentation.feature.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.usecase.AddFavorite
import com.numq.mvarchitecture.usecase.GetFavorites
import com.numq.mvarchitecture.usecase.RemoveFavorite
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel
constructor(
    private val getFavorites: GetFavorites,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite
) : ViewModel() {

    private val _error = MutableSharedFlow<Exception?>()
    val error = _error.asSharedFlow()

    private val _favorites = MutableStateFlow<List<Image>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private var indexToRemove: Int? = null

    private val onError: (Exception) -> Unit = { e ->
        viewModelScope.launch {
            _error.emit(e)
        }
    }

    private val onFavorites: (List<Image>) -> Unit = { list ->
        _favorites.update {
            it.plus(list).distinctBy { img -> img.id }
        }
    }

    private val onRemoveFavorite: (Image) -> Unit = { image ->
        indexToRemove = favorites.value.indexOfFirst { it.id == image.id }
        _favorites.update {
            it.filter { img -> img.id != image.id }
        }
    }

    private val onUndoRemoval: (Image) -> Unit = { image ->
        indexToRemove?.let { idx ->
            _favorites.update {
                it.subList(0, idx).plus(image).plus(it.subList(idx, it.size))
            }
        }
        indexToRemove = null
    }

    fun loadMore(skip: Int, limit: Int) = getFavorites.invoke(Pair(skip, limit)) {
        it.fold(onError, onFavorites)
    }

    fun removeFavorite(image: Image) = removeFavorite.invoke(image) {
        it.fold(onError, onRemoveFavorite)
    }

    fun undoRemoval(image: Image) = addFavorite.invoke(image) {
        it.fold(onError, onUndoRemoval)
    }
}

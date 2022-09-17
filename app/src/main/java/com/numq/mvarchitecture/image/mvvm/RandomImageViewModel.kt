package com.numq.mvarchitecture.image.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.numq.mvarchitecture.image.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RandomImageViewModel constructor(
    private val checkFavorite: CheckFavorite,
    private val getRandomImage: GetRandomImage,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite
) : ViewModel() {

    private val _error = MutableSharedFlow<Exception?>()
    val error = _error.asSharedFlow()

    private val _randomImage = MutableStateFlow<Image?>(null)
    val randomImage = _randomImage.asStateFlow()

    private val onError: (Exception) -> Unit = { e ->
        viewModelScope.launch {
            _error.emit(e)
        }
    }

    private val onImage: (Image) -> Unit = { image ->
        _randomImage.update { image }
    }

    fun randomImage(size: ImageSize) = getRandomImage(size, onError, onImage)

    fun updateImage(id: String) = checkFavorite(id, onError) { state ->
        _randomImage.update { current -> current?.copy(isFavorite = state) }
    }

    fun addFavorite(image: Image) = addFavorite.invoke(image, onError, onImage)

    fun removeFavorite(image: Image) = removeFavorite.invoke(image, onError, onImage)

}
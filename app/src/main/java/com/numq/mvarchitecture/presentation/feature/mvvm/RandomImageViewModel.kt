package com.numq.mvarchitecture.presentation.feature.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.entity.ImageSize
import com.numq.mvarchitecture.usecase.AddFavorite
import com.numq.mvarchitecture.usecase.CheckFavorite
import com.numq.mvarchitecture.usecase.GetRandomImage
import com.numq.mvarchitecture.usecase.RemoveFavorite
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

    fun randomImage(size: ImageSize) = getRandomImage(size) {
        it.fold(onError, onImage)
    }

    fun updateImage(id: String) = checkFavorite(id) {
        it.fold(onError) { state ->
            _randomImage.update { current -> current?.copy(isFavorite = state) }
        }
    }

    fun addFavorite(image: Image) = addFavorite.invoke(image) {
        it.fold(onError, onImage)
    }

    fun removeFavorite(image: Image) = removeFavorite.invoke(image) {
        it.fold(onError, onImage)
    }
}
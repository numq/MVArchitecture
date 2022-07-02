package com.numq.mvarchitecture.presentation.feature.mvp.favorites

import com.numq.mvarchitecture.domain.entity.Image
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavoritesView : FavoritesContract.View {

    override val error: MutableSharedFlow<Exception> = MutableSharedFlow()

    override var indexToRemove: Int? = null

    private val _favorites = MutableStateFlow<List<Image>>(emptyList())
    override val favorites = _favorites.asStateFlow()

    override fun onFavorites(images: List<Image>) {
        _favorites.update {
            val firstIndex = it.indexOfFirst { image -> images.contains(image) }
            val newList = if (firstIndex < 0) it else it.subList(0, firstIndex)
            newList.plus(images).distinct()
        }
    }

    override fun onRemoveFavorite(image: Image) {
        indexToRemove = favorites.value.indexOfFirst { it.id == image.id }
        _favorites.update {
            it.filter { img -> img.id != image.id }
        }
    }

    override fun onUndoRemoval(image: Image) {
        indexToRemove?.let { idx ->
            _favorites.update {
                it.toMutableList().apply {
                    add(idx, image)
                    toList()
                }
            }
        }
        indexToRemove = null
    }

}
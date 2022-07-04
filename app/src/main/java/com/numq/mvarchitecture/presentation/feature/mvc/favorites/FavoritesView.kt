package com.numq.mvarchitecture.presentation.feature.mvc.favorites

import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.presentation.feature.mvc.base.View
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavoritesView : View.Favorites {

    override val error: MutableSharedFlow<Exception> = MutableSharedFlow()

    override var indexToRemove: Int? = null

    private val _favorites = MutableStateFlow<List<Image>>(emptyList())
    override val favorites = _favorites.asStateFlow()

    override fun onFavorites(images: List<Image>) {
        _favorites.update { images }
    }

    override fun onRemoveFavorite(image: Image) {
        _favorites.update {
            indexToRemove = it.indexOfFirst { img -> img.id == image.id }
            it.filter { img -> img.id != image.id }
        }
    }

    override fun onUndoRemoval(image: Image) {
        indexToRemove?.let { idx ->
            _favorites.update {
                it.subList(0, idx).plus(image).plus(it.subList(idx, it.size))
            }
            indexToRemove = null
        }
    }

}
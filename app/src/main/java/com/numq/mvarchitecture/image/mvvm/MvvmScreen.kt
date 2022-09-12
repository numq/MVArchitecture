package com.numq.mvarchitecture.image.mvvm

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.numq.mvarchitecture.error.ErrorMessage
import com.numq.mvarchitecture.image.ImageScreen
import com.numq.mvarchitecture.navigation.Route
import org.koin.androidx.compose.getViewModel

@Composable
fun MvvmScreen(
    route: Route,
    scaffoldState: ScaffoldState,
    randomImageViewModel: RandomImageViewModel = getViewModel(),
    favoritesViewModel: FavoritesViewModel = getViewModel()
) {

    randomImageViewModel.error.collectAsState(null).value?.let {
        ErrorMessage(it, scaffoldState)
    }
    favoritesViewModel.error.collectAsState(null).value?.let {
        ErrorMessage(it, scaffoldState)
    }

    ImageScreen(
        route = route,
        randomImage = randomImageViewModel.randomImage.collectAsState().value,
        getRandomImage = randomImageViewModel::randomImage,
        favorites = favoritesViewModel.favorites.collectAsState().value,
        loadMore = favoritesViewModel::loadMore,
        updateImage = randomImageViewModel::updateImage,
        addFavorite = randomImageViewModel::addFavorite,
        removeFavorite = randomImageViewModel::removeFavorite,
        removeFromFavorites = favoritesViewModel::removeFavorite,
        undoRemoval = favoritesViewModel::undoRemoval
    )
}
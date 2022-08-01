package com.numq.mvarchitecture.image.mvc

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.numq.mvarchitecture.error.ShowError
import com.numq.mvarchitecture.image.ImageScreen
import com.numq.mvarchitecture.image.mvc.base.Controller
import com.numq.mvarchitecture.image.mvc.base.View
import com.numq.mvarchitecture.navigation.Route
import org.koin.androidx.compose.get

@Composable
fun MvcScreen(
    route: Route,
    scaffoldState: ScaffoldState,
    controller: Controller = get(),
    randomImageView: View.RandomImage = get(),
    favoritesView: View.Favorites = get()
) {

    randomImageView.error.collectAsState(null).value?.let {
        ShowError(scaffoldState, it)
    }
    favoritesView.error.collectAsState(null).value?.let {
        ShowError(scaffoldState, it)
    }

    ImageScreen(
        route = route,
        randomImage = randomImageView.randomImage.collectAsState().value,
        getRandomImage = controller::randomImage,
        updateImage = controller::updateImage,
        addFavorite = controller::addFavorite,
        removeFavorite = controller::removeFavorite,
        favorites = favoritesView.favorites.collectAsState().value,
        loadMore = controller::loadMore,
        removeFromFavorites = controller::removeFromFavorites,
        undoRemoval = controller::undoRemoval
    )
}
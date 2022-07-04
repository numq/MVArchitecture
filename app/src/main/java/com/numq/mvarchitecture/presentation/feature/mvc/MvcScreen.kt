package com.numq.mvarchitecture.presentation.feature.mvc

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.numq.mvarchitecture.platform.navigation.Route
import com.numq.mvarchitecture.presentation.component.error.ShowError
import com.numq.mvarchitecture.presentation.component.image.ImageScreen
import com.numq.mvarchitecture.presentation.feature.mvc.base.Controller
import com.numq.mvarchitecture.presentation.feature.mvc.base.View
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
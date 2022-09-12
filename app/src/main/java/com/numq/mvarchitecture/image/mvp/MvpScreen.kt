package com.numq.mvarchitecture.image.mvp

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.numq.mvarchitecture.error.ErrorMessage
import com.numq.mvarchitecture.image.ImageScreen
import com.numq.mvarchitecture.image.mvp.favorites.FavoritesContract
import com.numq.mvarchitecture.image.mvp.random.RandomImageContract
import com.numq.mvarchitecture.navigation.Route
import org.koin.androidx.compose.get

@Composable
fun MvpScreen(
    route: Route,
    scaffoldState: ScaffoldState,
    randomImageView: RandomImageContract.View = get(),
    randomImagePresenter: RandomImageContract.Presenter = get(),
    favoritesView: FavoritesContract.View = get(),
    favoritesPresenter: FavoritesContract.Presenter = get()
) {

    LaunchedEffect(Unit) {
        randomImagePresenter.attachView(randomImageView)
        favoritesPresenter.attachView(favoritesView)
    }

    randomImageView.error.collectAsState(null).value?.let {
        ErrorMessage(it, scaffoldState)
    }
    favoritesView.error.collectAsState(null).value?.let {
        ErrorMessage(it, scaffoldState)
    }

    ImageScreen(
        route = route,
        randomImage = randomImageView.randomImage.collectAsState().value,
        getRandomImage = randomImagePresenter::randomImage,
        updateImage = randomImagePresenter::updateImage,
        addFavorite = randomImagePresenter::addFavorite,
        removeFavorite = randomImagePresenter::removeFavorite,
        favorites = favoritesView.favorites.collectAsState().value,
        loadMore = favoritesPresenter::loadMore,
        removeFromFavorites = favoritesPresenter::removeFavorite,
        undoRemoval = favoritesPresenter::undoRemoval
    )
}
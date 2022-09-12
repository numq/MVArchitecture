package com.numq.mvarchitecture.image.mvi

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.numq.mvarchitecture.error.ErrorMessage
import com.numq.mvarchitecture.image.ImageScreen
import com.numq.mvarchitecture.image.mvi.favorites.FavoritesEffect
import com.numq.mvarchitecture.image.mvi.favorites.FavoritesEvent
import com.numq.mvarchitecture.image.mvi.favorites.FavoritesFeature
import com.numq.mvarchitecture.image.mvi.image.RandomImageEffect
import com.numq.mvarchitecture.image.mvi.image.RandomImageEvent
import com.numq.mvarchitecture.image.mvi.image.RandomImageFeature
import com.numq.mvarchitecture.navigation.Route
import org.koin.androidx.compose.get

@Composable
fun MviScreen(
    route: Route,
    scaffoldState: ScaffoldState,
    randomImageFeature: RandomImageFeature = get(),
    favoritesFeature: FavoritesFeature = get()
) {

    when (val effect = randomImageFeature.effect.collectAsState(null).value) {
        is RandomImageEffect.ShowError -> ErrorMessage(effect.exception, scaffoldState)
        else -> Unit
    }

    when (val effect = favoritesFeature.effect.collectAsState(null).value) {
        is FavoritesEffect.ShowError -> ErrorMessage(effect.exception, scaffoldState)
        else -> Unit
    }

    ImageScreen(
        route = route,
        randomImage = randomImageFeature.state.collectAsState().value.randomImage,
        getRandomImage = { randomImageFeature.dispatch(RandomImageEvent.GetRandomImage(it)) },
        updateImage = { randomImageFeature.dispatch(RandomImageEvent.UpdateImage(it)) },
        addFavorite = { randomImageFeature.dispatch(RandomImageEvent.AddFavorite(it)) },
        removeFavorite = { randomImageFeature.dispatch(RandomImageEvent.RemoveFavorite(it)) },
        favorites = favoritesFeature.state.collectAsState().value.favorites,
        loadMore = { skip, limit ->
            favoritesFeature.dispatch(
                FavoritesEvent.LoadMore(
                    skip,
                    limit
                )
            )
        },
        removeFromFavorites = { favoritesFeature.dispatch(FavoritesEvent.RemoveFavorite(it)) },
        undoRemoval = { favoritesFeature.dispatch(FavoritesEvent.UndoRemoval(it)) }
    )
}
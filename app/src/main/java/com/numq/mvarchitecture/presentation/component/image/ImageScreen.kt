package com.numq.mvarchitecture.presentation.component.image

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.entity.ImageSize
import com.numq.mvarchitecture.platform.navigation.Route

@Composable
fun ImageScreen(
    route: Route,
    randomImage: Image? = null,
    getRandomImage: (ImageSize) -> Unit,
    updateImage: (String) -> Unit,
    addFavorite: (Image) -> Unit,
    removeFavorite: (Image) -> Unit,
    favorites: List<Image>,
    loadMore: (Int, Int) -> Unit,
    removeFromFavorites: (Image) -> Unit,
    undoRemoval: (Image) -> Unit
) {

    val navController = rememberNavController()
    val imageRoute = object {
        val randomImage = "random_image"
        val favorites = "favorites"
    }

    NavHost(
        navController,
        startDestination = imageRoute.randomImage
    ) {
        composable(imageRoute.randomImage) {
            RandomImage(
                title = route.name,
                image = randomImage,
                getRandomImage = getRandomImage,
                updateImage = updateImage,
                addFavorite = addFavorite,
                removeFavorite = removeFavorite
            ) {
                navController.navigate(imageRoute.favorites) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(imageRoute.favorites) {
            Favorites(
                title = route.name,
                favorites = favorites,
                getFavorites = loadMore,
                removeFromFavorites = removeFromFavorites,
                undoRemoval = undoRemoval
            )
        }
    }
}
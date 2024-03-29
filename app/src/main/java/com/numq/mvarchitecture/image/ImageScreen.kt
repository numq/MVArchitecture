package com.numq.mvarchitecture.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.numq.mvarchitecture.navigation.Route

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

    /**
     * This flag should be declared out of nested NavHost to prevent recreation
     */
    val (gridMode, setGridMode) = rememberSaveable {
        mutableStateOf(false)
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
                undoRemoval = undoRemoval,
                gridMode = gridMode, setGridMode = setGridMode
            )
        }
    }
}
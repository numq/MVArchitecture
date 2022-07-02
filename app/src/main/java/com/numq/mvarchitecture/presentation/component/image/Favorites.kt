package com.numq.mvarchitecture.presentation.component.image

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.platform.constant.AppConstants
import com.numq.mvarchitecture.presentation.component.paging.PagingList
import kotlinx.coroutines.launch

@Composable
fun Favorites(
    title: String,
    favorites: List<Image>,
    getFavorites: (Int, Int) -> Unit,
    removeFromFavorites: (Image) -> Unit,
    undoRemoval: (Image) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()

    val (gridMode, setGridMode) = rememberSaveable {
        mutableStateOf(false)
    }

    val removalSnackbar: (Image) -> Unit = { img ->
        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        removeFromFavorites(img)
        coroutineScope.launch {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                "Removed from favorites.",
                "UNDO"
            )
            if (result == SnackbarResult.ActionPerformed) {
                undoRemoval(img)
                if (gridMode) gridState.animateScrollToItem(favorites.indexOf(img))
                else listState.animateScrollToItem(favorites.indexOf(img))
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBar(title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = title)
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            setGridMode(!gridMode)
        }) {
            Icon(if (gridMode) Icons.Rounded.ViewList else Icons.Rounded.GridView, "")
        }
    }) { values ->
        BoxWithConstraints(
            modifier = Modifier.padding(values),
            contentAlignment = Alignment.TopCenter
        ) {
            PagingList(
                Modifier.fillMaxSize(),
                listState,
                gridState,
                favorites,
                getFavorites,
                AppConstants.Paging.DEFAULT_SIZE,
                gridMode = gridMode
            ) { img ->
                FavoriteImageItem(Modifier, img, removalSnackbar)
            }
        }
    }
}
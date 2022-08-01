package com.numq.mvarchitecture.image

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Collections
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.ImageSearch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun RandomImage(
    title: String,
    image: Image?,
    getRandomImage: (ImageSize) -> Unit,
    updateImage: (String) -> Unit,
    addFavorite: (Image) -> Unit,
    removeFavorite: (Image) -> Unit,
    openFavorites: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()

    val (size, setSize) = remember {
        mutableStateOf(ImageSize(500, 500))
    }

    val (imageLoaded, setImageLoaded) = remember {
        mutableStateOf(false)
    }

    val (currentImage, setCurrentImage) = remember {
        mutableStateOf<Image?>(null)
    }

    val (currentPainter, setCurrentPainter) = remember {
        mutableStateOf<Painter?>(null)
    }

    LaunchedEffect(Unit) {
        if (image == null) {
            getRandomImage(size)
        } else {
            updateImage(image.id)
        }
    }

    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBar(title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = title)
            }
        })
    }, floatingActionButton = {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            FloatingActionButton(onClick = {
                openFavorites()
            }) {
                Icon(Icons.Rounded.Collections, "")
            }
            Spacer(Modifier.width(8.dp))
            FloatingActionButton(modifier = Modifier.scale(1.2f), onClick = {
                if (imageLoaded) {
                    image?.let {
                        if (image.isFavorite) removeFavorite(image) else addFavorite(image)
                    }
                }
            }) {
                if (imageLoaded) {
                    image?.let {
                        Icon(
                            Icons.Rounded.Favorite,
                            "",
                            tint = if (image.isFavorite) Color.Red else Color.Unspecified
                        )
                    }
                } else {
                    CircularProgressIndicator()
                }
            }
            Spacer(Modifier.width(8.dp))
            Row {
                FloatingActionButton(onClick = {
                    if (imageLoaded) {
                        setCurrentImage(image)
                        setImageLoaded(false)
                        getRandomImage(size)
                    }
                }) {
                    Icon(Icons.Rounded.ImageSearch, "")
                }
                Column {
                    // TODO: size picker
                }
            }
        }
    }, floatingActionButtonPosition = FabPosition.Center) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            image?.let {
                SubcomposeAsyncImage(
                    model = image.downloadUrl,
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    onSuccess = {
                        setImageLoaded(true)
                    },
                    success = {
                        setCurrentPainter(it.painter)
                        setCurrentImage(image)
                    })
            }
            if (currentImage != null && currentPainter != null) {
                RandomImageItem(currentImage, currentPainter)
            }
        }
    }
}
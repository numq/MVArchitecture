package com.numq.mvarchitecture.presentation.component.image

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.numq.mvarchitecture.domain.entity.Image

@Composable
fun FavoriteImageItem(
    modifier: Modifier = Modifier,
    image: Image,
    onItemClick: (Image) -> Unit
) {
    AsyncImage(
        model = image.downloadUrl,
        contentDescription = "",
        modifier = modifier.clickable { onItemClick(image) },
        contentScale = ContentScale.FillWidth
    )
}
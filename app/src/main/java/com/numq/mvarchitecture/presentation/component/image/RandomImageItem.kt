package com.numq.mvarchitecture.presentation.component.image

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.numq.mvarchitecture.domain.entity.Image

@Composable
fun RandomImageItem(image: Image, painter: Painter) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter, "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .scale(2f)
                .background(Color.Black)
                .alpha(.3f)
        )
        Box(contentAlignment = Alignment.TopEnd) {
            Image(painter = painter, "", modifier = Modifier.fillMaxWidth())
            AnimatedVisibility(visible = image.isFavorite) {
                Icon(
                    Icons.Rounded.Favorite,
                    "",
                    modifier = Modifier
                        .scale(1.5f)
                        .padding(16.dp),
                    tint = Color.Red
                )
            }
        }
    }
}
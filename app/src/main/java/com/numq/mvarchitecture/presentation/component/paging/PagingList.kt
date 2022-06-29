package com.numq.mvarchitecture.presentation.component.paging

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun <T> PagingList(
    modifier: Modifier = Modifier,
    data: List<T>,
    loadData: (Int, Int) -> Unit,
    pageSize: Int,
    initPageSize: Int = pageSize * 3,
    pagePreload: Int = 2,
    gridMode: Boolean,
    onItemClick: @Composable (T) -> Unit = {}
) {

    val (page, setPage) = remember {
        mutableStateOf(0)
    }

    val (currentIndex, setCurrentIndex) = remember {
        mutableStateOf(0)
    }

    val (maxIndex, setMaxIndex) = remember {
        mutableStateOf(0)
    }

    val skip = initPageSize + page * pageSize

    LaunchedEffect(Unit) {
        loadData(0, initPageSize)
    }

    LaunchedEffect(page) {
        loadData(skip, pageSize)
    }

    LaunchedEffect(currentIndex) {
        processIndex(currentIndex, maxIndex - pageSize - pagePreload, maxIndex - pagePreload, {
            setPage(maxOf(0, page - 1))
        }, {
            setPage(page + 1)
        })
    }

    if (gridMode) {
        LazyVerticalGrid(GridCells.Fixed(2), modifier) {
            itemsIndexed(data) { idx, item ->
                setCurrentIndex(idx)
                setMaxIndex(maxOf(maxIndex, idx))
                onItemClick(item)
            }
        }
    } else {
        LazyColumn(modifier) {
            itemsIndexed(data) { idx, item ->
                setCurrentIndex(idx)
                setMaxIndex(maxOf(maxIndex, idx))
                onItemClick(item)
            }
        }
    }
}

fun processIndex(
    currentIndex: Int,
    minAnchor: Int,
    maxAnchor: Int,
    pageIncrement: () -> Unit,
    pageDecrement: () -> Unit
) {
    if (currentIndex < minAnchor) pageDecrement()
    if (currentIndex > maxAnchor) pageIncrement()
}
package com.numq.mvarchitecture.paging

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun <T> PagingList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    gridState: LazyGridState = rememberLazyGridState(),
    data: List<T>,
    loadData: (Int, Int) -> Unit,
    pageSize: Int = 15,
    pagePreload: Int = 2,
    gridMode: Boolean,
    onItem: @Composable (T) -> Unit = {}
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

    LaunchedEffect(Unit, page) {
        loadData(page * pageSize, pageSize)
    }

    LaunchedEffect(currentIndex) {
        processIndex(currentIndex, maxIndex - pageSize - pagePreload, maxIndex - pagePreload, {
            setPage(maxOf(0, page - 1))
        }, {
            setPage(page + 1)
        })
    }

    if (gridMode) {
        LazyVerticalGrid(GridCells.Fixed(2), modifier, state = gridState) {
            itemsIndexed(data) { idx, item ->
                setCurrentIndex(idx)
                setMaxIndex(maxOf(maxIndex, idx))
                onItem(item)
            }
        }
    } else {
        LazyColumn(modifier, state = listState) {
            itemsIndexed(data) { idx, item ->
                setCurrentIndex(idx)
                setMaxIndex(maxOf(maxIndex, idx))
                onItem(item)
            }
        }
    }
}
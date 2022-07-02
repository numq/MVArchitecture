package com.numq.mvarchitecture.presentation.component.paging

import android.util.Log
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

    val skip = when(page){
        0 -> 0
        1 -> initPageSize
        else -> initPageSize + (pageSize * page)
    }
    val limit = if (page > 0) pageSize else initPageSize

    LaunchedEffect(Unit, page) {
        Log.e(javaClass.simpleName, "$page,$skip,$limit")
        loadData(skip, limit)
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
                onItemClick(item)
            }
        }
    } else {
        LazyColumn(modifier, state = listState) {
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
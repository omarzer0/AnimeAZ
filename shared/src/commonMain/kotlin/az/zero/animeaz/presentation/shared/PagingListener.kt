package az.zero.animeaz.presentation.shared

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PagingListener(listState: LazyGridState, buffer: Int = 5, onLoadMore: (() -> Unit)? = null) {
    var lastTotalItems = -1
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            val loadMore =
                lastVisibleItemIndex > (totalItemsNumber - buffer) && (lastTotalItems != totalItemsNumber)

            // FIXME Adding this condition makes the visibility of loading composable so slow
            // !listState.isScrollInProgress

            loadMore && layoutInfo.totalItemsCount != 0
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                if (it) {
                    lastTotalItems = listState.layoutInfo.totalItemsCount
                    onLoadMore?.invoke()
                }
            }
    }
}
package az.zero.animeaz.presentation.composables

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope

fun LazyGridItemSpanScope.getSpanAdaptive(): GridItemSpan {
    return GridItemSpan(maxLineSpan)
}

fun getSpan(cellsNumber: Int): (LazyGridItemSpanScope) -> GridItemSpan {
    return { GridItemSpan(cellsNumber) }
}
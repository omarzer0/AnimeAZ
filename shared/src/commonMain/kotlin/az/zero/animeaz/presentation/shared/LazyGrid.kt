package az.zero.animeaz.presentation.shared

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope

fun getSpan(cellsNumber: Int): (LazyGridItemSpanScope) -> GridItemSpan {
    return { GridItemSpan(cellsNumber) }
}
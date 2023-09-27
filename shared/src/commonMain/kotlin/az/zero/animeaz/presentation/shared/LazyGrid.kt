package az.zero.animeaz.presentation.shared

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope

private val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }

fun getSpan(cellsNumber: Int): (LazyGridItemSpanScope) -> GridItemSpan {
    return { GridItemSpan(cellsNumber) }
}
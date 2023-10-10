@file:OptIn(ExperimentalMaterial3Api::class)

package az.zero.animeaz.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import az.zero.animeaz.SharedRes
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.presentation.shared.AnimeItem
import az.zero.animeaz.presentation.shared.AppDivider
import az.zero.animeaz.presentation.shared.BasicHeaderWithBackBtn
import az.zero.animeaz.presentation.shared.ErrorWithRetry
import az.zero.animeaz.presentation.shared.LoadingComposable
import az.zero.animeaz.presentation.shared.PagingListener
import az.zero.animeaz.presentation.shared.TextWithClearIcon
import az.zero.animeaz.presentation.shared.getSpan
import az.zero.animeaz.presentation.string_util.StringHelper
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun SearchScreen(
    onBackPressed: () -> Unit,
    onAnimeClick: (Anime) -> Unit
) {

    val viewModel = rememberOnRoute(instanceClass = SearchViewModel::class) { SearchViewModel(it) }
    val query by viewModel.searchQuery.collectAsState()

    val spanCount = 3
    val searchScreenState by viewModel.searchScreenState.collectAsState()
    val animeList = searchScreenState.animeList

    val listState = rememberLazyGridState()
    PagingListener(listState = listState) { viewModel.loadMore() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchHeader(
                text = query,
                onTextValueChanged = { viewModel.updateSearchQuery(it) },
                onBackPressed = onBackPressed,
                onClearClick = { viewModel.updateSearchQuery("") }
            )

        }
    ) {
        when {
            searchScreenState.initialLoadingError != null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = StringHelper.getStringRes(id = SharedRes.strings.network_error),
                    )
                }
            }

            searchScreenState.isInitialLoading -> {
                LoadingComposable(color = Color.Red)
            }

            animeList.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = StringHelper.getStringRes(id = SharedRes.strings.no_result),
                    )
                }
            }
            else -> {
                LazyVerticalGrid(
                    modifier = Modifier.padding(it),
                    columns = GridCells.Fixed(spanCount),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    state = listState
                ) {
                    items(animeList) { anime ->
                        AnimeItem(anime = anime, onClick = onAnimeClick)
                    }

                    if (searchScreenState.isLoadingMore) {
                        item(span = getSpan(spanCount)) {
                            LoadingComposable(
                                modifier = Modifier.fillMaxWidth().height(200.dp),
                                color = Color.Blue
                            )
                        }
                    }

                    searchScreenState.loadingMoreError?.let {
                        item(span = getSpan(spanCount)) {
                            ErrorWithRetry(
                                errorBodyText = StringHelper.getStringRes(
                                    SharedRes.strings.home_load_more_error
                                ),
                                retryButtonText = StringHelper.getStringRes(
                                    SharedRes.strings.retry_btn_text
                                ),
                                onRetryClick = viewModel::loadMore
                            )
                        }
                    }

                }

            }

        }

    }
}

@Composable
fun SearchHeader(
    text: String,
    onTextValueChanged: (String) -> Unit,
    onBackPressed: () -> Unit,
    onClearClick: () -> Unit,
) {
    Column {
        BasicHeaderWithBackBtn(
            onBackPressed = onBackPressed,
            textContent = {
                TextWithClearIcon(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    text = text,
                    hint = StringHelper.getStringRes(SharedRes.strings.search),
                    onClearClick = onClearClick,
                    onTextValueChanged = onTextValueChanged
                )
            }
        )

        AppDivider()
    }
}
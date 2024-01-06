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
import az.zero.animeaz.core.PlatformName.ANDROID
import az.zero.animeaz.core.PlatformName.DESKTOP
import az.zero.animeaz.core.PlatformName.IOS
import az.zero.animeaz.core.getPlatformName
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.presentation.composables.AnimeItem
import az.zero.animeaz.presentation.composables.AppDivider
import az.zero.animeaz.presentation.composables.BasicHeaderWithBackBtn
import az.zero.animeaz.presentation.composables.ErrorWithRetry
import az.zero.animeaz.presentation.composables.LoadingComposable
import az.zero.animeaz.presentation.composables.PagingListener
import az.zero.animeaz.presentation.composables.TextWithClearIcon
import az.zero.animeaz.presentation.composables.getSpanAdaptive
import dev.icerock.moko.resources.compose.stringResource
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun SearchScreen(
    onBackPressed: () -> Unit,
    onAnimeClick: (Anime) -> Unit
) {
    val minItemSize = when (getPlatformName()) {
        ANDROID, IOS -> 100.dp
        DESKTOP -> 300.dp
    }

    val viewModel = rememberOnRoute(instanceClass = SearchViewModel::class) { SearchViewModel(it) }
    val query by viewModel.searchQuery.collectAsState()

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
                        text = stringResource(SharedRes.strings.network_error),
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
                        text = stringResource(SharedRes.strings.no_result),
                    )
                }
            }

            else -> {
                LazyVerticalGrid(
                    modifier = Modifier.padding(it),
                    columns = GridCells.Adaptive(minItemSize),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    state = listState
                ) {
                    items(animeList) { anime ->
                        AnimeItem(anime = anime, onClick = onAnimeClick)
                    }

                    if (searchScreenState.isLoadingMore) {
                        item(span = { getSpanAdaptive() }) {
                            LoadingComposable(
                                modifier = Modifier.fillMaxWidth().height(200.dp),
                                color = Color.Blue
                            )
                        }
                    }

                    searchScreenState.loadingMoreError?.let {
                        item(span = { getSpanAdaptive() }) {
                            ErrorWithRetry(
                                errorBodyText = stringResource(
                                    SharedRes.strings.home_load_more_error
                                ),
                                retryButtonText = stringResource(
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
                    hint = stringResource(SharedRes.strings.search),
                    onClearClick = onClearClick,
                    onTextValueChanged = onTextValueChanged
                )
            }
        )

        AppDivider()
    }
}
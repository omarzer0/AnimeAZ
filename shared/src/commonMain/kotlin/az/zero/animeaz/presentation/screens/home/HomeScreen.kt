@file:OptIn(ExperimentalMaterial3Api::class)

package az.zero.animeaz.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import az.zero.animeaz.SharedRes
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.presentation.shared.AnimeItem
import az.zero.animeaz.presentation.shared.ErrorWithRetry
import az.zero.animeaz.presentation.shared.LoadingComposable
import az.zero.animeaz.presentation.shared.PagingListener
import az.zero.animeaz.presentation.shared.getSpan
import az.zero.animeaz.presentation.string_util.StringHelper
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun HomeScreen(
    onAnimeClick: (Anime) -> Unit,
    onSearchClick: () -> Unit,
) {

    val spanCount = 3
    val viewModel = rememberOnRoute(instanceClass = HomeViewModel::class) { HomeViewModel(it) }
    val homeScreenState by viewModel.animeListState.collectAsState()
    val animeList = homeScreenState.animeList

    val listState = rememberLazyGridState()
    PagingListener(listState = listState) { viewModel.loadMore() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopAppBar(
                onDrawerClick = {},
                onSearchClick = onSearchClick
            )
        }
    ) {
        when {
            homeScreenState.initialLoadingError != null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = StringHelper.getStringRes(id = SharedRes.strings.network_error),
                    )
                }
            }

            homeScreenState.isInitialLoading -> {
                LoadingComposable(color = Color.Red)
            }

            else -> {
                LazyVerticalGrid(
                    modifier = Modifier.padding(it),
                    columns = GridCells.Fixed(spanCount),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    state = listState
                ) {
                    items(animeList) { anime -> AnimeItem(anime = anime, onClick = onAnimeClick) }

                    if (homeScreenState.isLoadingMore) {
                        item(span = getSpan(spanCount)) {
                            LoadingComposable(
                                modifier = Modifier.fillMaxWidth().height(200.dp),
                                color = Color.Blue
                            )
                        }
                    }

                    homeScreenState.loadingMoreError?.let {
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
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    onDrawerClick: () -> Unit,
    onSearchClick: () -> Unit
) {

    // TODO 1: Use Large appbar with scrolling behavior
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = StringHelper.getStringRes(SharedRes.strings.appName),
                fontWeight = FontWeight.Bold,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onDrawerClick,
                content = {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Filled.Menu,
                        contentDescription = StringHelper.getStringRes(SharedRes.strings.drawer),
                    )
                }
            )
        },
        actions = {
            IconButton(
                onClick = onSearchClick,
                content = {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Filled.Search,
                        contentDescription = StringHelper.getStringRes(SharedRes.strings.search),
                    )
                }
            )
        }
    )

}


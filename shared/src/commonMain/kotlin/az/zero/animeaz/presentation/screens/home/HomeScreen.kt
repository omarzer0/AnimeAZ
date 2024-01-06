@file:OptIn(ExperimentalMaterial3Api::class)

package az.zero.animeaz.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import az.zero.animeaz.SharedRes
import az.zero.animeaz.core.PlatformName
import az.zero.animeaz.core.getPlatformName
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.presentation.composables.AnimeItem
import az.zero.animeaz.presentation.composables.AppDivider
import az.zero.animeaz.presentation.composables.ErrorWithRetry
import az.zero.animeaz.presentation.composables.LoadingComposable
import az.zero.animeaz.presentation.composables.PagingListener
import az.zero.animeaz.presentation.composables.ScrollWrapper
import az.zero.animeaz.presentation.composables.getSpanAdaptive
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.pullrefresh.rememberPullRefreshState
import io.github.xxfast.decompose.router.rememberOnRoute
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onAnimeClick: (Anime) -> Unit,
    onSearchClick: () -> Unit,
    onFavListClick: () -> Unit,
) {
    val minItemSize = when (getPlatformName()) {
        PlatformName.ANDROID, PlatformName.IOS -> 100.dp
        PlatformName.DESKTOP -> 300.dp
    }

    val viewModel = rememberOnRoute(instanceClass = HomeViewModel::class) { HomeViewModel() }
    val homeScreenState by viewModel.animeListState.collectAsState()
    val animeList = homeScreenState.animeList
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val useBioAuth by viewModel.useBioAuth.collectAsState()
    val listState = rememberLazyGridState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeScreenState.isRefreshing,
        onRefresh = viewModel::refresh
    )

    PagingListener(listState = listState) { viewModel.loadMore() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            HomeModalSheetContent(
                useBioAuth = useBioAuth
            ) {
                viewModel.onUseBioAuthChanged(it)
            }
        },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                HomeTopAppBar(
                    onDrawerClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    onSearchClick = onSearchClick,
                    onFavListClick = onFavListClick
                )
            }
        ) {
            ScrollWrapper(
                modifier = Modifier.fillMaxSize().padding(it),
                isRefreshing = homeScreenState.isRefreshing,
                pullRefreshState = pullRefreshState
            ) {

                when {
                    homeScreenState.mainError != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(SharedRes.strings.network_error),
                            )
                        }
                    }

                    homeScreenState.mainLoading -> {
                        LoadingComposable(color = Color.Red)
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minItemSize),
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            state = listState
                        ) {
                            items(animeList, key = { it.id }) { anime ->
                                AnimeItem(
                                    anime = anime,
                                    onClick = onAnimeClick
                                )
                            }

                            if (homeScreenState.isLoadingMore) {
                                item(span = { getSpanAdaptive() }) {
                                    LoadingComposable(
                                        modifier = Modifier.fillMaxWidth().height(200.dp),
                                        color = Color.Blue
                                    )
                                }
                            }

                            homeScreenState.loadingMoreError?.let {
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
    }

}

@Composable
fun HomeModalSheetContent(
    useBioAuth: Boolean,
    onUseBioAuthClick: (Boolean) -> Unit,
) {

    ModalDrawerSheet {

        Box(
            modifier = Modifier.fillMaxWidth().height(200.dp).clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(SharedRes.strings.loginHeaderPlaceHolder),
                style = MaterialTheme.typography.titleLarge
            )
        }

        AppDivider()

        TextSwitch(
            modifier = Modifier.padding(16.dp),
            name = stringResource(SharedRes.strings.lock_with_biometry),
            change = useBioAuth,
            onCheckedChange = onUseBioAuthClick
        )
    }
}

@Composable
private fun TextSwitch(
    modifier: Modifier = Modifier,
    name: String,
    change: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Switch(
            checked = change,
            onCheckedChange = { onCheckedChange(it) }
        )

        Spacer(Modifier.width(8.dp))
        Text(text = name)
    }
}

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    onDrawerClick: () -> Unit,
    onSearchClick: () -> Unit,
    onFavListClick: () -> Unit
) {

    // TODO 1: Use Large appbar with scrolling behavior
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        modifier = modifier,
        title = {
            Text(
                text = stringResource(SharedRes.strings.appName),
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
                        contentDescription = stringResource(SharedRes.strings.drawer),
                    )
                }
            )
        },
        actions = {
            IconButton(
                onClick = onFavListClick,
                content = {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(SharedRes.strings.favorite),
                    )
                }
            )

            IconButton(
                onClick = onSearchClick,
                content = {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(SharedRes.strings.search),
                    )
                }
            )
        }
    )

}



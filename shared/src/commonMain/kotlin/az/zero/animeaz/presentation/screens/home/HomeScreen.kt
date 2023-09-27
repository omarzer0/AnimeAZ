@file:OptIn(ExperimentalMaterial3Api::class)

package az.zero.animeaz.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import az.zero.animeaz.SharedRes
import az.zero.animeaz.data.remote.util.ResponseState
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.presentation.shared.clickableSafeClick
import az.zero.animeaz.presentation.shared.rememberDefaultPainter
import az.zero.animeaz.presentation.stringUtil.StringHelper
import az.zero.animeaz.presentation.theme.CustomColors.DarkOrange
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun HomeScreen() {

    val spanCount = 3
    val viewModel = rememberOnRoute(instanceClass = HomeViewModel::class) { HomeViewModel(it) }
    val state = viewModel.animeList.collectAsState(ResponseState.Empty())
    val animeList = state.value.data?: emptyList()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopAppBar(
                onDrawerClick = {},
                onSearchClick = {}
            )
        }
    ) {

        LazyVerticalGrid(
            modifier = Modifier.padding(it),
            columns = GridCells.Fixed(spanCount),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            items(animeList) {
                AnimeItem(it) {

                }
            }

        }
    }


}

@Composable
fun AnimeItem(
    anime: Anime,
    modifier: Modifier = Modifier,
    onClick: (anime: Anime) -> Unit
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickableSafeClick { onClick(anime) }
            .padding(4.dp),
    ) {
        val image = rememberDefaultPainter(url = anime.image)
        val animeShowState = StringHelper.getStringRes(
            if (anime.airingStatus) SharedRes.strings.onAir else SharedRes.strings.finished
        )

        Image(
            modifier = Modifier.fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = image,
            contentScale = ContentScale.Crop,
            contentDescription = StringHelper.getStringRes(SharedRes.strings.animeImage)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = anime.englishName,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = StringHelper.getStringRes(SharedRes.strings.ratingStar),
                tint = DarkOrange
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "${anime.score}",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = animeShowState,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

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



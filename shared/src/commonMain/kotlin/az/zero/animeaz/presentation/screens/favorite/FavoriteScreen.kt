@file:OptIn(ExperimentalMaterial3Api::class)

package az.zero.animeaz.presentation.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import az.zero.animeaz.SharedRes
import az.zero.animeaz.domain.model.FavAnime
import dev.icerock.moko.resources.compose.stringResource
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun FavoriteScreen(
    onBackClick: () -> Unit
) {
    val viewModel =
        rememberOnRoute(instanceClass = FavoriteViewModel::class) { FavoriteViewModel() }

    val favAnimeList by viewModel.favAnimeList.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FavoriteTopBar(
                title = stringResource(SharedRes.strings.favorite),
                onBackClick = onBackClick
            )
        }
    ) {

        if (favAnimeList.isEmpty()){
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(SharedRes.strings.your_fav_anime_list_is_empty),
                )
            }
        }else{
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                items(favAnimeList, key = { it.id }) {
                    FavAnimeItem(it) { viewModel.removeFromFavourite(it) }
                }
            }
        }

    }
}

@Composable
fun FavAnimeItem(
    anime: FavAnime,
    modifier: Modifier = Modifier,
    onRemoveFromFavClick: (FavAnime) -> Unit
) {

    val showStatus =
        stringResource(if (anime.airingStatus) SharedRes.strings.onAir else SharedRes.strings.finished)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 8.dp, start = 16.dp, end = 8.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(8.dp)),

        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(140.dp),
                bitmap = anime.image,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Text(
                    text = anime.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = showStatus,
                        maxLines = 1,
                    )

                    IconButton(
                        onClick = { onRemoveFromFavClick(anime) },
                        content = {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = stringResource(SharedRes.strings.favorite),
                                tint = Color.Red
                            )
                        }
                    )
                }

                Text(
                    text = anime.typeOfShowWithNumberOfEpisodes,
                    maxLines = 1,
                )

            }
        }
    }
}

@Composable
fun FavoriteTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(SharedRes.strings.back),
                    )
                }
            )
        },
    )
}

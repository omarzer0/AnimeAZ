package az.zero.animeaz.presentation.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import az.zero.animeaz.SharedRes
import az.zero.animeaz.presentation.shared.clickableSafeClick
import az.zero.animeaz.presentation.string_util.StringHelper
import az.zero.animeaz.presentation.theme.CustomColors
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun FavoriteScreen() {
    val viewModel =
        rememberOnRoute(instanceClass = FavoriteViewModel::class) { FavoriteViewModel() }

    val favAnimeList by viewModel.favAnimeList.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {

        LazyColumn {
            items(favAnimeList) {
                FavAnimeItem(it) { viewModel.removeFromFavourite(it) }
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

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickableSafeClick(onLongClick = { onRemoveFromFavClick(anime) })
            .padding(4.dp),
    ) {
        val animeShowState = StringHelper.getStringRes(
            if (anime.airingStatus) SharedRes.strings.onAir else SharedRes.strings.finished
        )

        Image(
            modifier = Modifier.fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            bitmap = anime.image,
            contentScale = ContentScale.Crop,
            contentDescription = StringHelper.getStringRes(SharedRes.strings.animeImage)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = anime.name,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = StringHelper.getStringRes(SharedRes.strings.ratingStar),
                tint = CustomColors.DarkOrange
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
package az.zero.animeaz.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import az.zero.animeaz.SharedRes
import az.zero.animeaz.core.PlatformName.ANDROID
import az.zero.animeaz.core.PlatformName.DESKTOP
import az.zero.animeaz.core.PlatformName.IOS
import az.zero.animeaz.core.getPlatformName
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.presentation.string_util.StringHelper
import az.zero.animeaz.presentation.theme.CustomColors.DarkOrange

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
            .padding(4.dp)
    ) {
        val url = when (getPlatformName()) {
            ANDROID, IOS -> anime.image
            DESKTOP -> anime.cover
        }

        val image = rememberDefaultPainter(url = url)
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
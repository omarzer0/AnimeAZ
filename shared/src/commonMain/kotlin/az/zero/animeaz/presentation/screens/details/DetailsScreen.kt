@file:OptIn(ExperimentalAnimationApi::class)

package az.zero.animeaz.presentation.screens.details

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import az.zero.animeaz.SharedRes
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.presentation.shared.rememberDefaultPainter
import az.zero.animeaz.presentation.string_util.StringHelper
import az.zero.animeaz.presentation.theme.CustomColors
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun DetailsScreen(anime: Anime) {
    val viewModel = rememberOnRoute(instanceClass = DetailsViewModel::class) {
        DetailsViewModel(it)
    }

    val scrollState = rememberScrollState()
    val image = rememberDefaultPainter(url = anime.cover)
    val rating = buildAnnotatedString {
        val score = anime.score.toString()
        append(score)
        addStyle(
            style = SpanStyle(fontWeight = FontWeight.ExtraBold, fontSize = 20.sp),
            0,
            score.length
        )
        append("/10")
    }
    val showStatus =
        StringHelper.getStringRes(if (anime.airingStatus) SharedRes.strings.onAir else SharedRes.strings.finished)

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top
        ) {
            Box {

                Image(
                    modifier = Modifier.fillMaxWidth().height(250.dp),
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    alpha = 0.2f
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 140.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
                        .height(200.dp)

                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(140.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Text(
                            text = anime.englishName,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = showStatus,
                            maxLines = 1,
                        )

                        Text(
                            text = anime.seasonWithYear,
                            maxLines = 1,
                        )

                        Text(
                            text = anime.typeOfShowWithNumberOfEpisodes,
                            maxLines = 1,
                        )


                    }

                }


            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = Icons.Outlined.StarHalf,
                        contentDescription = null,
                        tint = CustomColors.Orange
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(text = rating)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = anime.reviewCount.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                DetailsIconHeader(
                    icon = Icons.Default.Tag,
                    header = "Rank",
                    body = anime.rank.toString(),
                )

                DetailsIconHeader(
                    icon = Icons.Filled.Star,
                    header = "Popularity",
                    body = anime.popularity.toString(),
                )

            }


            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Magenta)
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(anime.genres, key = { it.id }) {
                        CustomChipButton(it.name)
                    }
                }
                anime.genres.isNotEmpty().apply {
                    Spacer(Modifier.height(8.dp))
                }

                ExpandedText(
                    modifier = Modifier.fillMaxWidth(),
                    text = anime.description,
                )
            }

        }

    }
}

@Composable
fun CustomChipButton(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    backgroundColor: Color = MaterialTheme.colorScheme.onPrimary
) {

    Box(
        modifier = modifier
            .padding(8.dp)
            .background(backgroundColor, RoundedCornerShape(40.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }

}

@Composable
fun ExpandedText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    collapsedTextLines: Int = 3
) {


    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded }
            .padding(8.dp),
    ) {
        Text(
            text = text,
            style = style,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedTextLines,
            overflow = TextOverflow.Ellipsis
        )
    }

}


@Composable
fun RowScope.DetailsIconHeader(
    icon: ImageVector,
    header: String,
    headerStyle: TextStyle = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 16.sp),
    body: String?,
    bodyStyle: TextStyle = MaterialTheme.typography.bodySmall,
    iconTint: Color = MaterialTheme.colorScheme.onBackground,
    contentDescription: String? = null
) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint
        )
        Spacer(Modifier.height(8.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = headerStyle,
            text = header,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(4.dp))
        body?.let {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = it,
                style = bodyStyle
            )
        }
    }
}
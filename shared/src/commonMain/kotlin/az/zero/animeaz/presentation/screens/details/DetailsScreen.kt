package az.zero.animeaz.presentation.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.presentation.shared.rememberDefaultPainter
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun DetailsScreen(anime: Anime) {
    val viewModel =
        rememberOnRoute(instanceClass = DetailsViewModel::class) { DetailsViewModel(it) }

    val scrollState = rememberScrollState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Red).verticalScroll(scrollState)
        ) {
            val image = rememberDefaultPainter(url = anime.cover)

            Image(
                modifier = Modifier.fillMaxWidth().height(250.dp).background(Color.Blue),
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )

        }

    }
}
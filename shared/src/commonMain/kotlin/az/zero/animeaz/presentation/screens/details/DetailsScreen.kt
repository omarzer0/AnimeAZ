package az.zero.animeaz.presentation.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun DetailsScreen() {
    val viewModel =
        rememberOnRoute(instanceClass = DetailsViewModel::class) { DetailsViewModel(it) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


        }

    }
}
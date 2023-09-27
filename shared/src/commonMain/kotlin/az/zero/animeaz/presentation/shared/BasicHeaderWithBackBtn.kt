@file:OptIn(ExperimentalMaterial3Api::class)

package az.zero.animeaz.presentation.shared

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

@Composable
fun BasicHeaderWithBackBtn(
    modifier: Modifier = Modifier,
    textContent: @Composable () -> Unit,
    onBackPressed: () -> Unit,
    backgroundColor: Color = Color.Black,
    darkBackButton: Boolean = backgroundColor.luminance() > 0.5f,
    actions: @Composable RowScope.() -> Unit = {},
) {

    val backButtonTint = if (darkBackButton) Color.Black else Color.White
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = textContent,
        actions = actions,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = backgroundColor
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackPressed
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    "Back",
                    tint = backButtonTint,
                )
            }
        }
    )
}

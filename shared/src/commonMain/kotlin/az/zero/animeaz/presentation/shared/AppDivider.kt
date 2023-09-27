package az.zero.animeaz.presentation.shared

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppDivider(
    paddingValues: PaddingValues = PaddingValues()
) {
    Divider(
        modifier = Modifier.padding(paddingValues),
        thickness = 1.dp,
        color = Color.Gray.copy(alpha = 0.5f)
    )
}

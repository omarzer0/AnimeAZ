package az.zero.animeaz.presentation.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.PullRefreshState
import dev.materii.pullrefresh.pullRefresh

@Composable
fun ScrollWrapper(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    pullRefreshState: PullRefreshState,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.pullRefresh(pullRefreshState)
    ) {
        content()

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

}
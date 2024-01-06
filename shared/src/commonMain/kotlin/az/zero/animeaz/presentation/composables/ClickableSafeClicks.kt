package az.zero.animeaz.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import kotlinx.datetime.Clock

internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = Clock.System.now().toEpochMilliseconds()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= 300L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.clickableSafeClick(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    onLongClickLabel: String? = null,
    role: Role? = null,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
        properties["onLongClick"] = onLongClick
        properties["onDoubleClick"] = onDoubleClick
    }
) {
    val onClickEventsCutter = remember { MultipleEventsCutter.get() }

    Modifier.combinedClickable(
        enabled = enabled,
        onClick = { onClickEventsCutter.processEvent { onClick?.invoke() } },
        onLongClick = onLongClick,
        onDoubleClick = onDoubleClick,
        onLongClickLabel = onLongClickLabel,
        onClickLabel = onClickLabel,
        role = role,
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }

    )
}


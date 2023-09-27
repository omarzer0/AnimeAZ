package az.zero.animeaz.presentation.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TextWithClearIcon(
    modifier: Modifier = Modifier,
    text: String,
    hint: String = "",
    onTextValueChanged: (String) -> Unit,
    cursorBrush: Color = Color.Red,
    cursorHandleColor: Color = Color.Red,
    cursorSelectedRegionColor: Color = Color.Red,
    textColor: Color = Color.White,
    onClearClick: () -> Unit,
) {

    val customTextSelectionColors = TextSelectionColors(
        handleColor = cursorHandleColor,
        backgroundColor = cursorSelectedRegionColor
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        val focusManager = LocalFocusManager.current
        var isHintDisplayed by rememberSaveable { mutableStateOf(hint != "") }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                BasicTextField(
                    value = text,
                    maxLines = 1,
                    singleLine = true,
                    cursorBrush = SolidColor(cursorBrush),
                    onValueChange = {
                        if (text != it) {
                            onTextValueChanged(it)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = textColor,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged {
                            isHintDisplayed = !it.isFocused && text.isEmpty()
                        },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text
                    ), keyboardActions = KeyboardActions(onSearch = {
                        focusManager.clearFocus()
                    })
                )
            }

            if (!isHintDisplayed && text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onClearClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "Clear"
                    )
                }
            }
        }

        if (isHintDisplayed) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = hint,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = textColor
                )
            )
        }
    }
}
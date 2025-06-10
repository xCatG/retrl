package com.example.editor.ui

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.KeyEventType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyboardInterceptor(
    modifier: Modifier = Modifier,
    onCharTyped: (Char) -> Unit,
    onEnter: () -> Unit,
    onBackspace: () -> Unit
) {
    var textState by remember { mutableStateOf("") } // Dummy state for BasicTextField

    BasicTextField(
        value = textState,
        onValueChange = { /* BasicTextField needs this but we intercept keys directly */ },
        modifier = modifier.onKeyEvent { keyEvent ->
            if (keyEvent.type == KeyEventType.KeyUp) { // Process on key release
                when (keyEvent.key) {
                    Key.Enter -> {
                        onEnter()
                        true
                    }
                    Key.Backspace -> {
                        onBackspace()
                        true
                    }
                    else -> {
                        // This is a simplified way to get characters.
                        // A more robust solution would handle modifiers (Shift, etc.)
                        // and different keyboard layouts or use InputConnection.
                        val char = keyEvent.utf16CodePoint.toChar()
                        if (!char.isISOControl()) { // Filter out control characters
                            onCharTyped(char)
                            true
                        } else {
                            false
                        }
                    }
                }
            } else {
                false
            }
        }
    )
}

package com.cattailsw.retrl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.automirrored.filled.KeyboardReturn
import androidx.compose.material.icons.automirrored.filled.KeyboardTab
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cattailsw.retrl.ui.theme.RetroTypewriterTheme

@Composable
fun TypewriterToolbar(
    modifier: Modifier = Modifier,
    onReturn: () -> Unit,
    onTab: () -> Unit,
    onBackspace: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = onReturn) {
                Icon(Icons.AutoMirrored.Filled.KeyboardReturn, contentDescription = "Carriage Return")
            }
            IconButton(onClick = onTab) {
                Icon(Icons.AutoMirrored.Filled.KeyboardTab, contentDescription = "Tab")
            }
            IconButton(onClick = onBackspace) {
                Icon(Icons.AutoMirrored.Filled.Backspace, contentDescription = "Backspace")
            }
        }
    }
}

@Preview
@Composable
fun PreviewTypewriterToolbar() {
    RetroTypewriterTheme {
        TypewriterToolbar(onReturn = {}, onTab = {}, onBackspace = {})
    }
}

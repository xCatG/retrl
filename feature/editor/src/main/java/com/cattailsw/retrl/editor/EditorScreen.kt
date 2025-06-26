package com.cattailsw.retrl.editor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.cattailsw.retrl.ui.theme.RetroTypewriterTheme

@Composable
fun EditorScreen(viewModel: EditorViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    RetroTypewriterTheme {
        if (state.drawnEvents.isEmpty()) {
            Text("Start typing...", modifier = Modifier.fillMaxSize())
        } else {
            Text(text = state.drawnEvents.map { it.char }.joinToString(""))
        }
    }
}

package com.example.editor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
// import androidx.hilt.navigation.compose.hiltViewModel // Uncomment when Hilt is used
import com.example.editor.viewmodel.EditorViewModel // Adjust import if needed
import com.example.editor.state.PlaybackState // Adjust import

@Composable
fun EditorScreen(
    // viewModel: EditorViewModel = hiltViewModel() // Hilt injected ViewModel
    viewModel: EditorViewModel // For now, assume it's passed or manually created
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Editor Screen - Loading: ${uiState.isLoading}")
        Text("Character to type: A") // Placeholder for interaction
        // Add TypewriterCanvas, KeyboardInterceptor, PlaybackControls here
        TypewriterCanvas(
            modifier = Modifier.weight(1f),
            events = uiState.drawnEvents,
            cursor = uiState.currentCursorPosition
        )
        KeyboardInterceptor(
            onCharTyped = { viewModel.onCharacterTyped(it) },
            onEnter = { viewModel.onEnterPressed() },
            onBackspace = { viewModel.onBackspacePressed() }
        )
        PlaybackControls(
            playbackState = uiState.playbackState,
            onAction = { /* Handle playback actions */ }
        )
    }
}

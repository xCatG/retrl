package com.example.editor.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.editor.state.PlaybackState // Adjust import

sealed class PlaybackAction {
    object Play : PlaybackAction()
    object Pause : PlaybackAction()
    data class SetSpeed(val speed: Float) : PlaybackAction()
}

@Composable
fun PlaybackControls(
    playbackState: PlaybackState,
    onAction: (PlaybackAction) -> Unit
) {
    Row {
        Button(onClick = { onAction(PlaybackAction.Play) }) {
            Text("Play")
        }
        Button(onClick = { onAction(PlaybackAction.Pause) }) {
            Text("Pause")
        }
        // Add more controls for speed, etc.
        Text(text = "State: ${playbackState::class.java.simpleName}")
    }
}

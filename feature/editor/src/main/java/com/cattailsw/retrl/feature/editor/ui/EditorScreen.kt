package com.cattailsw.retrl.feature.editor.ui // Updated package

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cattailsw.retrl.core.ui.common.InfoDialog // Assuming InfoDialog is in this package
import com.cattailsw.retrl.core.ui.theme.VintageTypewriterAppTheme // Updated import
import com.cattailsw.retrl.feature.editor.state.ExportFormat
import com.cattailsw.retrl.feature.editor.state.PlaybackState // Updated import
import com.cattailsw.retrl.feature.editor.viewmodel.EditorViewModel // Updated import

// Data class for playback actions
internal data class PlaybackAction(
    val onTogglePlay: () -> Unit,
    val onSpeedChange: (Float) -> Unit,
    // val onSeek: (Float) -> Unit // Future: for seeking in playback
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    viewModel: EditorViewModel = hiltViewModel(),
    sessionId: String?, // Nullable for new sessions
    onNavigateFurther: (String) -> Unit // Callback for navigation
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val density = LocalDensity.current

    // Canvas bitmap state - this is where the drawing would be captured
    var canvasBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // This would be called by TypewriterCanvas when it has a new bitmap
    val onBitmapUpdated: (Bitmap) -> Unit = { newBitmap ->
        canvasBitmap = newBitmap
    }

    VintageTypewriterAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(uiState.currentSession?.title ?: "New Session") },
                    actions = {
                        IconButton(onClick = { viewModel.onSaveSession() }) {
                            Icon(Icons.Filled.Save, contentDescription = "Save Session")
                        }
                        IconButton(onClick = { viewModel.onShowExportDialog(true) }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "Export Options")
                        }
                        // Add more actions like settings, load, etc.
                    }
                )
            },
            // FloatingActionButton for play/pause or other primary action
            // floatingActionButton = {
            //     FloatingActionButton(onClick = { /* TODO */ }) {
            //         Icon(Icons.Filled.Edit, "Start Typing")
            //     }
            // }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // The TypewriterCanvas Composable
                TypewriterCanvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Canvas takes up most space
                    paperConfig = uiState.paperConfig,
                    keystrokes = uiState.currentKeystrokes,
                    onBitmapUpdated = onBitmapUpdated, // Pass the callback here
                    // Pass other necessary state like current text, cursor position, etc.
                    // onKeyEvent = { keyEvent, x, y -> viewModel.onKeyEvent(keyEvent, x, y) } // If canvas handles key input directly
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Playback Controls
                PlaybackControls(
                    playbackState = uiState.playbackState,
                    actions = PlaybackAction(
                        onTogglePlay = { viewModel.onTogglePlayback() },
                        onSpeedChange = { speed -> viewModel.onPlaybackSpeedChange(speed) }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // TODO: This is where the physical keyboard input would be captured
                // For now, it's commented out as KeyboardInterceptorComposable is not implemented
                // KeyboardInterceptorComposable(viewModel = viewModel)
                // Instead, we might have a simple text field for web/desktop, or rely on soft keyboard
                // For this task, direct key input handling is deferred.
                Text("Keyboard input handling placeholder", style = MaterialTheme.typography.bodySmall)

                // Example of a button to trigger a simulated key event (for testing)
                Button(onClick = {
                    // This is a mock key event.
                    // In a real scenario, this would come from an actual keyboard.
                    // val mockKeyEvent = KeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_A))
                    // viewModel.onKeyEvent(mockKeyEvent, 0f, 0f) // Pass appropriate x,y
                }) {
                    Text("Simulate Keystroke (Test)")
                }


                // --- Dialogs ---
                if (uiState.showExportDialog) {
                    ExportDialog(
                        onDismiss = { viewModel.onShowExportDialog(false) },
                        onExport = { title, format ->
                            canvasBitmap?.let { bmp ->
                                viewModel.onExport(bmp, title, format)
                            }
                            viewModel.onShowExportDialog(false)
                        },
                        currentBitmap = uiState.lastExportedBitmap ?: canvasBitmap
                    )
                }

                if (uiState.isLoading) {
                    CircularProgressIndicator()
                }

                uiState.error?.let { errorMsg ->
                    InfoDialog(title = "Error", message = errorMsg, onDismiss = { /* viewModel.clearError() */ })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportDialog(
    onDismiss: () -> Unit,
    onExport: (title: String, format: ExportFormat) -> Unit,
    currentBitmap: Bitmap?
) {
    var title by remember { mutableStateOf("TypewriterArt") }
    // var selectedFormat by remember { mutableStateOf(ExportFormat.PNG) } // If more formats

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Export Canvas") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("File Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Format: PNG") // Placeholder if only PNG is supported
                currentBitmap?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Preview:")
                    androidx.compose.foundation.Image( // Renamed to avoid conflict
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Export Preview",
                        modifier = Modifier.size(100.dp) // Small preview
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = { onExport(title, ExportFormat.PNG) }) {
                Text("Export")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Composable
fun PlaybackControls(
    playbackState: PlaybackState,
    actions: PlaybackAction,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = actions.onTogglePlay) {
            Icon(
                imageVector = if (playbackState.isPlaying) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                contentDescription = if (playbackState.isPlaying) "Stop Playback" else "Start Playback"
            )
        }
        // Add slider for playback speed
        Slider(
            value = playbackState.playbackSpeed,
            onValueChange = { actions.onSpeedChange(it) },
            valueRange = 0.5f..2.0f, // Example speed range
            steps = 5, // Example steps
            modifier = Modifier.weight(1f)
        )
        Text("${playbackState.playbackSpeed}x", style = MaterialTheme.typography.bodySmall)
    }
    // Display current playback progress (e.g., event index / total events)
    Text(
        "Event: ${playbackState.currentEventIndex} / ${playbackState.totalDurationMillis}", // totalDurationMillis is a placeholder here
        style = MaterialTheme.typography.bodySmall
    )
}

// Placeholder for Keyboard Interceptor Composable
// @Composable
// fun KeyboardInterceptorComposable(viewModel: EditorViewModel) {
//    // This composable would capture hardware keyboard events and forward them to the ViewModel.
//    // Implementation is platform-specific and can be complex.
//    // For Android, it might involve a FocusRequester and onKeyEvent modifier.
//    // For Desktop (Compose for Desktop), it might use LocalWindow.current.keyboard.
// }

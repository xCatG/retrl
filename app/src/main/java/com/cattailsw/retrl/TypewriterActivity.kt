package com.cattailsw.retrl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cattailsw.retrl.ui.theme.RetroTypewriterTheme

class TypewriterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetroTypewriterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TypingCanvas(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TypingCanvas(
    modifier: Modifier = Modifier,
    viewModel: TypewriterViewModel = viewModel()
) {
    val context = LocalContext.current
    val soundManager = remember { SoundManager(context) }
    
    // Dispose SoundManager when the composable leaves the composition
    DisposableEffect(Unit) {
        onDispose {
            soundManager.release()
        }
    }

    val textMeasurer = rememberTextMeasurer()
    val keystrokes = viewModel.keystrokes
    
    // Focus requester to ensure the hidden text field captures input
    val focusRequester = remember { FocusRequester() }

    // Input state
    var textInput by remember { mutableStateOf(TextFieldValue("")) }

    Box(modifier = modifier.fillMaxSize()) {
        // The Canvas renders the typewriter output
        val fullText = keystrokes.map { it.char }.joinToString("")
        Canvas(modifier = Modifier
            .fillMaxSize()
            .testTag("TypewriterCanvas")
            .semantics { contentDescription = fullText }
        ) {
            val style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 24.sp,
                color = Color.Black
            )
            
            // Measure grid based on a standard character
            val measureResult = textMeasurer.measure("M", style)
            val charWidth = measureResult.size.width.toFloat()
            val lineHeight = measureResult.size.height * 1.2f

            keystrokes.forEach { key ->
                val x = key.col * charWidth + 40f // Add some left margin
                val y = key.row * lineHeight + 40f // Add some top margin

                drawText(
                    textMeasurer = textMeasurer,
                    text = key.char.toString(),
                    topLeft = Offset(x, y),
                    style = style
                )
            }
        }

        // Hidden input field
        BasicTextField(
            value = textInput,
            onValueChange = { newValue ->
                val newText = newValue.text
                if (newText.isNotEmpty()) {
                    // Send each new character to the ViewModel
                    newText.forEach { char ->
                        viewModel.handleKey(char)
                        if (char == '\n') {
                            soundManager.playCarriageReturn()
                        } else {
                            soundManager.playKeyClick()
                        }
                    }
                    // Clear the input to keep it "stateless"
                    textInput = TextFieldValue("")
                } else {
                    // If empty, it might be a backspace that cleared it, 
                    // or it was already empty.
                    // We handle backspace via onKeyEvent mostly.
                    textInput = newValue
                }
            },
            modifier = Modifier
                .testTag("HiddenInput")
                .alpha(0f) // Invisible
                .focusRequester(focusRequester)
                .onKeyEvent { event ->
                    if (event.key == Key.Backspace && event.nativeKeyEvent.action == android.view.KeyEvent.ACTION_DOWN) {
                        viewModel.handleKey('\b')
                        soundManager.playKeyClick() // Backspace also clicks
                        true
                    } else if (event.key == Key.Enter && event.nativeKeyEvent.action == android.view.KeyEvent.ACTION_DOWN) {
                         viewModel.handleKey('\n')
                         soundManager.playCarriageReturn()
                         true
                    } else {
                        false
                    }
                },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.None
            )
        )
    }

    // Auto-focus the hidden field
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

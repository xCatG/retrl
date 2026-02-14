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
import com.cattailsw.retrl.ui.components.TypewriterToolbar
import com.cattailsw.retrl.ui.theme.RetroTypewriterTheme

class TypewriterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: TypewriterViewModel = viewModel()
            val context = LocalContext.current
            val soundManager = remember { SoundManager(context) }

            RetroTypewriterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        TypewriterToolbar(
                            onReturn = {
                                viewModel.handleKey('\n')
                                soundManager.playCarriageReturn()
                            },
                            onTab = { 
                                viewModel.handleTab() 
                            },
                            onBackspace = { 
                                viewModel.handleKey('\b')
                                soundManager.playKeyClick()
                            }
                        )
                    }
                ) { innerPadding ->
                    TypingCanvas(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        soundManager = soundManager
                    )
                }
            }
        }
    }
}

@Composable
fun TypingCanvas(
    modifier: Modifier = Modifier,
    viewModel: TypewriterViewModel,
    soundManager: SoundManager
) {
    DisposableEffect(Unit) {
        onDispose {
            soundManager.release()
        }
    }

    val textMeasurer = rememberTextMeasurer()
    val keystrokes = viewModel.keystrokes
    
    val focusRequester = remember { FocusRequester() }
    var textInput by remember { mutableStateOf(TextFieldValue("")) }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .testTag("TypewriterCanvas")
            .semantics { contentDescription = viewModel.getText() }
        ) {
            val style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 24.sp,
                color = Color.Black
            )
            
            val measureResult = textMeasurer.measure("M", style)
            viewModel.lineHeight = measureResult.size.height * 1.2f

            keystrokes.forEach { key ->
                if (key.char == '\n') return@forEach

                val x = key.x + 40f 
                val y = key.y + 40f

                drawText(
                    textMeasurer = textMeasurer,
                    text = key.char.toString(),
                    topLeft = Offset(x, y),
                    style = style
                )
            }
        }

        BasicTextField(
            value = textInput,
            onValueChange = { newValue ->
                val newText = newValue.text
                if (newText.isNotEmpty()) {
                    newText.forEach { char ->
                        viewModel.handleKey(char)
                        if (char == '\n') {
                            soundManager.playCarriageReturn()
                        } else {
                            soundManager.playKeyClick()
                        }
                    }
                    textInput = TextFieldValue("")
                } else {
                    textInput = newValue
                }
            },
            modifier = Modifier
                .testTag("HiddenInput")
                .alpha(0f) 
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.None
            )
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

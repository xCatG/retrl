package com.cattailsw.retrl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cattailsw.retrl.ui.TypingCanvas
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

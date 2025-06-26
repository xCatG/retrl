package com.cattailsw.retrl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cattailsw.retrl.editor.EditorScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypewriterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EditorScreen()
        }
    }
}

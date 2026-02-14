package com.cattailsw.retrl

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import org.junit.Rule
import org.junit.Test

class TypewriterTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun typingUpdatesCanvas() {
        // Start the app/component
        composeTestRule.setContent {
            val context = LocalContext.current
            val soundManager = remember { SoundManager(context) }
            TypingCanvas(viewModel = viewModel(), soundManager = soundManager)
        }

        // Find the hidden input and type "Hello"
        composeTestRule.onNodeWithTag("HiddenInput")
            .performTextInput("Hello")

        // Verify the canvas semantics description matches the typed text
        composeTestRule.onNodeWithTag("TypewriterCanvas")
            .assertContentDescriptionEquals("Hello")
    }

    @Test
    fun carriageReturnStartsNewLine() {
        composeTestRule.setContent {
            val context = LocalContext.current
            val soundManager = remember { SoundManager(context) }
            TypingCanvas(viewModel = viewModel(), soundManager = soundManager)
        }

        // Type "Hi" then Enter then "You"
        composeTestRule.onNodeWithTag("HiddenInput")
            .performTextInput("Hi\nYou")

        composeTestRule.onNodeWithTag("TypewriterCanvas")
            .assertContentDescriptionEquals("Hi\nYou")
    }
}

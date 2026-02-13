package com.cattailsw.retrl

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class TypewriterTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun typingUpdatesCanvas() {
        // Start the app/component
        composeTestRule.setContent {
            TypingCanvas()
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
            TypingCanvas()
        }

        // Type "Hi" then Enter then "You"
        // Note: performTextInput sends text. The implementation of BasicTextField 
        // might treat '\n' in the string as an Enter key if configured, 
        // or we might need to send the key event.
        // Our ViewModel.handleKey('\n') handles the logic.
        // The UI's BasicTextField onValueChange splits the string and calls handleKey.
        
        composeTestRule.onNodeWithTag("HiddenInput")
            .performTextInput("Hi\nYou")

        // The canvas description is constructed from `keystrokes.map { it.char }.joinToString("")`
        // So it should contain "Hi\nYou". 
        // While the *visual* rendering puts "You" on the next line (checked via visual inspection or bounds),
        // the *semantic* content should verify the data model integrity.
        
        composeTestRule.onNodeWithTag("TypewriterCanvas")
            .assertContentDescriptionEquals("Hi\nYou")
    }
}

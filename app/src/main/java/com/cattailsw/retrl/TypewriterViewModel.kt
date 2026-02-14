package com.cattailsw.retrl

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Keystroke(
    val char: Char,
    val x: Float,
    val y: Float,
    val angle: Float = 0f, // For future rotation
    val timestamp: Long = System.currentTimeMillis()
)

class TypewriterViewModel : ViewModel() {

    private val _keystrokes = mutableStateListOf<Keystroke>()
    val keystrokes: List<Keystroke> = _keystrokes

    // Cursor position in X/Y coordinates on the "paper"
    private var _currentX = 0f
    val cursorX: Float get() = _currentX

    private var _currentY = 0f
    val cursorY: Float get() = _currentY
    
    // We need to know the line height to advance correctly
    var lineHeight = 30f // A default, should be updated from the view

    /**
     * Returns the text content as a simple string.
     * This is a straightforward dump of characters and may not perfectly
     * represent the spatial layout if it becomes complex.
     */
    fun getText(): String {
        return keystrokes.map { it.char }.joinToString("")
    }

    fun handleKey(char: Char) {
        when (char) {
            '\n' -> newline()
            '\b' -> backspace()
            else -> typeCharacter(char)
        }
    }
    
    fun handleTab() {
        // A tab is just multiple spaces
        repeat(4) {
            handleKey(' ')
        }
    }

    private fun typeCharacter(char: Char) {
        // The actual width of the character should be measured by the UI,
        // but for the ViewModel, we can use an approximation or just advance
        // a fixed amount and let the UI place it. Here, we store the cursor
        // position, and the UI will use it.
        _keystrokes.add(Keystroke(char, _currentX, _currentY))
        
        // This is a simplification. The view should measure the char and tell us how much to advance.
        // For now, let's use a fixed-width estimate.
        _currentX += 15f 
    }

    private fun newline() {
        _currentY += lineHeight
        _currentX = 0f
        // We can also add a newline character to the keystroke list to preserve it in getText()
        _keystrokes.add(Keystroke('\n', _currentX, _currentY))
    }

    private fun backspace() {
        // This is complex now. Do we remove the last char? Or just move back?
        // Let's stick to just moving back for now.
        if (_keystrokes.isNotEmpty()) {
            val lastKey = _keystrokes.last()
            _currentX = lastKey.x
             // Optional: remove last keystroke
            // _keystrokes.removeLast()
        }
    }
    
    fun clear() {
        _keystrokes.clear()
        _currentX = 0f
        _currentY = 0f
    }
}

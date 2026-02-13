package com.cattailsw.retrl

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Keystroke(
    val char: Char,
    val col: Int,
    val row: Int,
    val timestamp: Long = System.currentTimeMillis()
)

class TypewriterViewModel : ViewModel() {

    private val _keystrokes = mutableStateListOf<Keystroke>()
    val keystrokes: List<Keystroke> = _keystrokes

    // Cursor position in grid units (characters/lines)
    // We expose these if the UI needs to draw a cursor.
    // For now, internal state is sufficient to determine where the *next* char goes.
    private var currentCol = 0
    private var currentRow = 0

    fun handleKey(char: Char) {
        when (char) {
            '\n' -> newline()
            '\b' -> backspace()
            else -> typeCharacter(char)
        }
    }

    private fun typeCharacter(char: Char) {
        _keystrokes.add(Keystroke(char, currentCol, currentRow))
        currentCol++
        // TODO: Implement margin bell or auto-return at max width
    }

    private fun newline() {
        currentRow++
        currentCol = 0
    }

    private fun backspace() {
        // Vintage behavior: just move the carriage back. 
        // Allows overtyping.
        if (currentCol > 0) {
            currentCol--
        } else if (currentRow > 0) {
             // Optional: move to end of previous line?
             // For now, let's stick to current line boundary or just stop.
        }
    }
    
    fun clear() {
        _keystrokes.clear()
        currentCol = 0
        currentRow = 0
    }
}

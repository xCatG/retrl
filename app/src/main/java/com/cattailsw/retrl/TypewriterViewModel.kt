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
    private var _currentCol = 0
    val cursorCol: Int get() = _currentCol

    private var _currentRow = 0
    val cursorRow: Int get() = _currentRow

    /**
     * Returns the text content as a reconstructed String.
     * This mimics reading the page from top-left to bottom-right.
     * Note: This is a simple reconstruction and might not perfectly represent
     * complex overtyping or spatial editing without a more robust model,
     * but it serves the "text format" requirement.
     */
    fun getText(): String {
        if (keystrokes.isEmpty()) return ""

        // Sort by row then column to reconstruct linear text
        val sortedKeys = keystrokes.sortedWith(compareBy<Keystroke> { it.row }.thenBy { it.col })
        
        val sb = StringBuilder()
        var lastRow = 0
        var lastCol = -1

        sortedKeys.forEach { key ->
            // Handle newlines
            while (key.row > lastRow) {
                sb.append('\n')
                lastRow++
                lastCol = -1
            }
            
            // Handle spaces (if we want to represent empty grid cells as spaces)
            // For now, we just append the chars. If exact spatial reconstruction is needed,
            // we'd pad with spaces. Let's do simple padding for a "text document" feel.
            while (key.col > lastCol + 1) {
                sb.append(' ')
                lastCol++
            }
            
            sb.append(key.char)
            lastCol = key.col
        }
        
        return sb.toString()
    }

    fun handleKey(char: Char) {
        when (char) {
            '\n' -> newline()
            '\b' -> backspace()
            else -> typeCharacter(char)
        }
    }

    private fun typeCharacter(char: Char) {
        _keystrokes.add(Keystroke(char, _currentCol, _currentRow))
        _currentCol++
        // TODO: Implement margin bell or auto-return at max width
    }

    private fun newline() {
        _currentRow++
        _currentCol = 0
    }

    private fun backspace() {
        // Vintage behavior: just move the carriage back. 
        // Allows overtyping.
        if (_currentCol > 0) {
            _currentCol--
        } else if (_currentRow > 0) {
             // Optional: move to end of previous line?
             // For now, let's stick to current line boundary or just stop.
        }
    }
    
    fun clear() {
        _keystrokes.clear()
        _currentCol = 0
        _currentRow = 0
    }
}

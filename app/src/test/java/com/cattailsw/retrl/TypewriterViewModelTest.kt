package com.cattailsw.retrl

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TypewriterViewModelTest {

    private lateinit var viewModel: TypewriterViewModel

    @Before
    fun setup() {
        viewModel = TypewriterViewModel()
    }

    @Test
    fun typingCharactersAddsToKeystrokes() {
        viewModel.handleKey('a')
        viewModel.handleKey('b')

        assertEquals(2, viewModel.keystrokes.size)
        assertEquals('a', viewModel.keystrokes[0].char)
        assertEquals('b', viewModel.keystrokes[1].char)
    }

    @Test
    fun newlineUpdatesCursorRowAndResetsCol() {
        viewModel.handleKey('a') // x 0 -> 15
        assertEquals(0f, viewModel.cursorY, 0.01f)
        assertEquals(15f, viewModel.cursorX, 0.01f)

        viewModel.handleKey('\n') // y 0 -> 30, x -> 0. Newline is added as a keystroke now too.
        assertEquals(30f, viewModel.cursorY, 0.01f)
        assertEquals(0f, viewModel.cursorX, 0.01f)

        viewModel.handleKey('b') // y 30, x 0 -> 15
        
        // keystrokes: 'a', '\n', 'b'
        val lastKey = viewModel.keystrokes.last()
        assertEquals(30f, lastKey.y, 0.01f)
        assertEquals(0f, lastKey.x, 0.01f)
        assertEquals('b', lastKey.char)
    }

    @Test
    fun backspaceMovesCursorBack() {
        viewModel.handleKey('a')
        assertEquals(15f, viewModel.cursorX, 0.01f)

        viewModel.handleKey('\b')
        // In the new implementation, backspace sets cursorX to the X of the last keystroke.
        // 'a' was at x=0. So backspace should move it back to 0.
        // Wait, 'a' is stored at x=0. After typing 'a', cursor is at 15.
        // handleKey('\b') -> if keystrokes not empty, last key is 'a' at 0. set cursorX = 0.
        assertEquals(0f, viewModel.cursorX, 0.01f)
        
        // Ensure keystroke 'a' is NOT removed (vintage behavior)
        assertEquals(1, viewModel.keystrokes.size)
    }

    @Test
    fun getTextReconstructsStringCorrectly() {
        viewModel.handleKey('H')
        viewModel.handleKey('i')
        
        assertEquals("Hi", viewModel.getText())
    }

    @Test
    fun getTextWithNewlines() {
        viewModel.handleKey('H')
        viewModel.handleKey('i')
        viewModel.handleKey('\n')
        viewModel.handleKey('T')
        viewModel.handleKey('h')
        viewModel.handleKey('e')
        viewModel.handleKey('r')
        viewModel.handleKey('e')

        // In the new implementation, \n is added to keystrokes list.
        // So getText() which just maps chars will work fine.
        val expected = "Hi\nThere"
        assertEquals(expected, viewModel.getText())
    }

    @Test
    fun getTextWithSpacesAndGaps() {
        viewModel.handleKey('H')
        viewModel.handleKey(' ')
        viewModel.handleKey('i')
        
        assertEquals("H i", viewModel.getText())
    }
}

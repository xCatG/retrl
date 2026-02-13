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
        viewModel.handleKey('a') // row 0, col 0 -> next col 1
        assertEquals(0, viewModel.cursorRow)
        assertEquals(1, viewModel.cursorCol)

        viewModel.handleKey('\n') // row 0 -> 1, col -> 0
        assertEquals(1, viewModel.cursorRow)
        assertEquals(0, viewModel.cursorCol)

        viewModel.handleKey('b') // row 1, col 0
        val lastKey = viewModel.keystrokes.last()
        assertEquals(1, lastKey.row)
        assertEquals(0, lastKey.col)
        assertEquals('b', lastKey.char)
    }

    @Test
    fun backspaceMovesCursorBack() {
        viewModel.handleKey('a')
        assertEquals(1, viewModel.cursorCol)

        viewModel.handleKey('\b')
        assertEquals(0, viewModel.cursorCol)
        
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

        val expected = "Hi\nThere"
        assertEquals(expected, viewModel.getText())
    }

    @Test
    fun getTextWithSpacesAndGaps() {
        // H _ i (simulating manual cursor movement or space)
        
        viewModel.handleKey('H')
        viewModel.handleKey(' ')
        viewModel.handleKey('i')
        
        assertEquals("H i", viewModel.getText())
    }
}

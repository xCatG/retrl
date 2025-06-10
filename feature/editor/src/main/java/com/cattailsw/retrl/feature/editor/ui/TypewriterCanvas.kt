package com.cattailsw.retrl.feature.editor.ui // Updated package

import android.graphics.Bitmap
import android.graphics.Matrix // For matrix operations if needed for transformations
import android.graphics.Paint // Android's Paint object for drawing
import android.graphics.Typeface // For setting custom fonts on Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.cattailsw.retrl.core.domain.model.KeystrokeEvent // Updated import
import com.cattailsw.retrl.feature.editor.state.PaperConfig // Updated import
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Define default paint properties (can be customized via state)
private val defaultTextPaint = Paint().apply {
    color = android.graphics.Color.BLACK // Default ink color
    textSize = 40f // Default font size in pixels (adjust based on PaperConfig)
    isAntiAlias = true
    // typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL) // Default font
    // TODO: Allow font and color to be set from PaperConfig or Theme
}

@Composable
fun TypewriterCanvas(
    modifier: Modifier = Modifier,
    paperConfig: PaperConfig,
    keystrokes: List<KeystrokeEvent>,
    onBitmapUpdated: (Bitmap) -> Unit,
    // onCanvasTap: (Offset) -> Unit // Callback for tap events if needed for cursor placement
    // currentCursorPosition: Offset? // To draw a cursor/caret
) {
    val density = LocalDensity.current

    // Convert Dp dimensions to pixels
    val paperWidthPx = with(density) { paperConfig.width.toPx() }
    val paperHeightPx = with(density) { paperConfig.height.toPx() }

    // State for the internal bitmap where drawing occurs
    // Multiplying by simulatedPixelRatio for a higher-resolution internal canvas
    val internalBitmapWidth = (paperWidthPx * paperConfig.simulatedPixelRatio).toInt()
    val internalBitmapHeight = (paperHeightPx * paperConfig.simulatedPixelRatio).toInt()

    // Remember the bitmap. It's recreated if dimensions change.
    val bitmap = remember(internalBitmapWidth, internalBitmapHeight, paperConfig.backgroundColor) {
        Bitmap.createBitmap(internalBitmapWidth, internalBitmapHeight, Bitmap.Config.ARGB_8888).apply {
            // Erase the bitmap with the paper color
            val canvas = android.graphics.Canvas(this)
            canvas.drawColor(paperConfig.backgroundColor.toArgbInt()) // Extension function below
        }
    }

    // Effect to redraw the bitmap whenever keystrokes or config changes
    LaunchedEffect(keystrokes, paperConfig, bitmap) {
        withContext(Dispatchers.Default) { // Perform drawing off the main thread
            val canvas = android.graphics.Canvas(bitmap)
            // Clear canvas with background color first
            canvas.drawColor(paperConfig.backgroundColor.toArgbInt())

            // Apply scaling factor for drawing based on simulatedPixelRatio
            canvas.scale(paperConfig.simulatedPixelRatio, paperConfig.simulatedPixelRatio)

            // Configure paint based on paperConfig or theme (simplified for now)
            val textPaint = Paint(defaultTextPaint).apply {
                // textSize = paperConfig.fontSize.toPx() // If fontSize is in Dp
                // typeface = paperConfig.fontStyle.getAndroidTypeface() // If FontStyle maps to Typeface
                // color = paper_config.inkColor.toArgbInt() // If InkColor defined
            }

            // Draw each keystroke event onto the bitmap
            // This is a simplified rendering. A real typewriter would have complex character placement,
            // line advance, carriage returns, etc.
            var currentX = 20f // Starting X position (margin)
            var currentY = 50f // Starting Y position (margin)
            val lineHeight = textPaint.textSize * 1.5f // Approximate line height

            keystrokes.forEach { event ->
                // TODO: This logic is extremely basic.
                // Needs to handle actual character widths, newlines, backspace, tabs, etc.
                // Also, event.xPosition and event.yPosition should ideally be used if they
                // represent absolute positions from a more sophisticated input system.
                // For now, we simulate simple text flow.

                if (event.char == KeystrokeEvent.SPECIAL_KEY_NEWLINE) {
                    currentY += lineHeight
                    currentX = 20f // Reset X to margin
                } else if (event.char == KeystrokeEvent.SPECIAL_KEY_BACKSPACE) {
                    // This is very hard to do correctly on a bitmap without re-rendering all text
                    // or managing text layout properly. For now, it's ignored in rendering.
                } else {
                    // Draw the character
                    canvas.drawText(event.char.toString(), currentX, currentY, textPaint)
                    // Advance X position (very rough estimate)
                    currentX += textPaint.measureText(event.char.toString())
                    // Word wrap (very basic)
                    if (currentX > paperWidthPx - 20f) { // Check against paper width minus margin
                        currentY += lineHeight
                        currentX = 20f
                    }
                }
            }
            onBitmapUpdated(bitmap) // Notify that the bitmap has changed
        }
    }

    // The Jetpack Compose Canvas where the bitmap is rendered
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    // TODO: Implement panning/scrolling of the canvas if larger than view
                    // This would involve updating a transformation matrix for the bitmap.
                }
            }
            // .pointerInput(Unit) {
            //     detectTapGestures(onTap = { offset -> onCanvasTap(offset) })
            // }
    ) {
        // Draw the bitmap onto this Compose Canvas
        // The bitmap itself is scaled by simulatedPixelRatio, so we draw it scaled down
        // to fit the display size.
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawBitmap(
                bitmap,
                0f,
                0f,
                null // Default paint for drawing bitmap
            )
            // TODO: Draw cursor/caret if needed, based on currentCursorPosition
            // Example:
            // currentCursorPosition?.let {
            //     drawLine(Color.Black, start = it, end = Offset(it.x, it.y + 20f), strokeWidth = 2f)
            // }
        }
    }
}

// Extension function to convert Compose Color to Android's int color
private fun Color.toArgbInt(): Int {
    return android.graphics.Color.argb(
        (this.alpha * 255.0f + 0.5f).toInt(),
        (this.red * 255.0f + 0.5f).toInt(),
        (this.green * 255.0f + 0.5f).toInt(),
        (this.blue * 255.0f + 0.5f).toInt()
    )
}

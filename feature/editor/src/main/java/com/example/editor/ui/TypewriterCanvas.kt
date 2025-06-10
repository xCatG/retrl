package com.example.editor.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.domain.KeystrokeEvent

@Composable
fun TypewriterCanvas(
    modifier: Modifier = Modifier,
    events: List<KeystrokeEvent>,
    cursor: Offset
) {
    // val textMeasurer = rememberTextMeasurer() // For Modifier.drawWithCache approach
    val context = LocalContext.current // For Paint if needed

    Canvas(modifier = modifier.fillMaxWidth().height(300.dp)) { // Example size
        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 40f
            }
            events.forEach { event ->
                canvas.nativeCanvas.drawText(
                    event.char.toString(),
                    event.xOffsetPx,
                    event.yOffsetPx,
                    paint
                )
            }
            // Draw cursor
            drawCircle(color = Color.Red, radius = 8f, center = cursor)
        }
    }
}

package com.cattailsw.retrl.core.domain.usecase

import android.graphics.Bitmap
import android.net.Uri
import com.cattailsw.retrl.core.domain.repository.TypewriterRepository
import com.cattailsw.retrl.core.domain.util.Result

class ExportCanvasAsBitmapUseCase(private val repository: TypewriterRepository) {
    suspend operator fun invoke(bitmap: Bitmap, title: String): Result<Uri> {
        if (title.isBlank()) {
            return Result.Failure(IllegalArgumentException("Title cannot be blank for export"))
        }
        // Add any other bitmap validation if necessary
        return repository.exportAsBitmap(bitmap, title)
    }
}

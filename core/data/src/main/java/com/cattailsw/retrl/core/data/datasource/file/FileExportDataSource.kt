package com.cattailsw.retrl.core.data.datasource.file // Updated package

import android.graphics.Bitmap
import android.net.Uri
import com.cattailsw.retrl.core.domain.util.Result // Updated import

interface FileExportDataSource {
    suspend fun saveBitmapToDownloads(bitmap: Bitmap, title: String): Result<Uri>
    // suspend fun saveVideoToDownloads(file: File, title: String): Result<Uri> // Example for video
}

package com.example.data

import android.graphics.Bitmap // Android specific
import android.net.Uri // Android specific

interface FileExportDataSource {
    suspend fun saveBitmapToPictures(bitmap: Bitmap, title: String): Result<Uri>
    // This could be extended for SAF, specific paths, etc.
    // suspend fun savePdfToDownloads(pages: List<Bitmap>, title: String): Result<Uri> // Example for PDF
}

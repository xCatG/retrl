package com.cattailsw.retrl.data

import android.graphics.Bitmap
import android.net.Uri

interface FileExportDataSource {
    suspend fun exportBitmap(bitmap: Bitmap, title: String): Uri?
    suspend fun exportPdf(pages: List<Bitmap>, title: String): Uri?
}

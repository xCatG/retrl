package com.cattailsw.retrl.core.data.datasource.file // Updated package

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.cattailsw.retrl.core.domain.util.Result // Updated import
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream
import javax.inject.Inject

class MediaStoreFileExportDataSource @Inject constructor(
    private val context: Context
) : FileExportDataSource {

    override suspend fun saveBitmapToDownloads(bitmap: Bitmap, title: String): Result<Uri> {
        return withContext(Dispatchers.IO) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, createFileName(title, "png"))
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/VintageTypewriter")
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }
            }

            val resolver = context.contentResolver
            var uri: Uri? = null
            var outputStream: OutputStream? = null

            try {
                uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                    ?: throw Exception("Failed to create new MediaStore record.")

                outputStream = resolver.openOutputStream(uri)
                    ?: throw Exception("Failed to get output stream for new MediaStore record.")

                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
                    throw Exception("Failed to save bitmap.")
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)
                }
                Result.Success(uri)
            } catch (e: Exception) {
                // If something Araws, delete the incomplete entry if it was created
                uri?.let { resolver.delete(it, null, null) }
                Result.Failure(Exception("Failed to save image to Downloads: ${e.localizedMessage}", e))
            } finally {
                outputStream?.close()
            }
        }
    }

    private fun createFileName(title: String, extension: String): String {
        val sanitizedTitle = title.replace(Regex("[^a-zA-Z0-9.-]"), "_")
        return "${sanitizedTitle}_${System.currentTimeMillis()}.$extension"
    }

    // Example for video (would require more complex handling)
    // override suspend fun saveVideoToDownloads(file: File, title: String): Result<Uri> {
    //     // Similar MediaStore logic for video files (e.g., video/mp4)
    //     return Result.Failure(UnsupportedOperationException("Video export not implemented"))
    // }
}

package com.example.data

import com.example.domain.TypewriterRepository
import com.example.domain.TypewriterSession
import com.example.domain.DomainBitmap // Using DomainBitmap from :core:domain
import com.example.domain.DomainUri // Using DomainUri from :core:domain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject // For Hilt

// We need to bridge DomainBitmap/DomainUri from the domain layer
// to actual android.graphics.Bitmap / android.net.Uri in this data layer.
// This can be done via type casting if we assume DomainBitmap is always a Bitmap
// or by defining proper mapping functions. For now, we'll assume direct casting for simplicity,
// but this is a point that might need refinement.
import android.graphics.Bitmap
import android.net.Uri

class DefaultTypewriterRepository @Inject constructor(
    private val localDataSource: SessionLocalDataSource,
    private val fileDataSource: FileExportDataSource
) : TypewriterRepository {

    override suspend fun saveSession(session: TypewriterSession): Result<Unit> {
        return localDataSource.saveSession(session)
    }

    override suspend fun getSession(id: String): Result<TypewriterSession> {
        return localDataSource.getSession(id)
    }

    override fun getSessionList(): Flow<List<TypewriterSession>> {
        return localDataSource.getSessionList()
    }

    override suspend fun exportAsBitmap(bitmap: DomainBitmap, title: String): Result<DomainUri> {
        // Assuming DomainBitmap is an Android Bitmap for this layer.
        // A proper implementation would involve type checking or a safer mapping.
        if (bitmap !is Bitmap) {
            return Result.failure(IllegalArgumentException("Provided bitmap is not an Android Bitmap"))
        }
        val result = fileDataSource.saveBitmapToPictures(bitmap, title)
        return result.map { it as DomainUri } // Cast Android Uri to DomainUri
    }

    override suspend fun exportAsPdf(pages: List<DomainBitmap>, title: String): Result<DomainUri> {
        // Similar assumption and casting for PDF pages.
        // This method will likely need a more concrete implementation for PDF generation.
        // For now, let's return a failure or a dummy result, as FileExportDataSource
        // doesn't have a PDF export method yet.
        // val androidPages = pages.mapNotNull { it as? Bitmap }
        // if (androidPages.size != pages.size) {
        //     return Result.failure(IllegalArgumentException("Some pages were not Android Bitmaps"))
        // }
        // return fileDataSource.savePdfToDownloads(androidPages, title) // Assuming such a method exists

        // Placeholder until FileExportDataSource is updated for PDF
        return Result.failure(NotImplementedError("PDF export not yet implemented in FileExportDataSource"))
    }
}

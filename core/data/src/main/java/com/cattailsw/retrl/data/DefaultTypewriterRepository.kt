package com.cattailsw.retrl.data

import android.graphics.Bitmap
import android.net.Uri
import com.cattailsw.retrl.domain.TypewriterRepository
import com.cattailsw.retrl.domain.TypewriterSession
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultTypewriterRepository @Inject constructor(
    private val localDataSource: SessionLocalDataSource,
    private val fileDataSource: FileExportDataSource
) : TypewriterRepository {
    override suspend fun saveSession(session: TypewriterSession) = runCatching {
        localDataSource.insert(session)
    }

    override suspend fun getSession(id: String) = runCatching {
        localDataSource.get(id) ?: throw IllegalArgumentException("Session not found")
    }

    override fun getSessionList(): Flow<List<TypewriterSession>> =
        localDataSource.getAll()

    override suspend fun exportAsBitmap(bitmap: Bitmap, title: String) = runCatching {
        fileDataSource.exportBitmap(bitmap, title) ?: throw IllegalStateException("Export failed")
    }

    override suspend fun exportAsPdf(pages: List<Bitmap>, title: String) = runCatching {
        fileDataSource.exportPdf(pages, title) ?: throw IllegalStateException("Export failed")
    }
}

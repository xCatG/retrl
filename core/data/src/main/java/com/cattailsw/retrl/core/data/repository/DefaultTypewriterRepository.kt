package com.cattailsw.retrl.core.data.repository // Updated package

import android.graphics.Bitmap
import android.net.Uri
import com.cattailsw.retrl.core.data.datasource.file.FileExportDataSource // Updated import
import com.cattailsw.retrl.core.data.datasource.local.SessionLocalDataSource // Updated import
// import com.cattailsw.retrl.core.data.datasource.local.db.model.SessionEntity // Not directly used here, localDataSource handles entity mapping
import com.cattailsw.retrl.core.domain.model.TypewriterSession // Updated import
import com.cattailsw.retrl.core.domain.repository.TypewriterRepository // Updated import
import com.cattailsw.retrl.core.domain.util.Result // Updated import
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultTypewriterRepository @Inject constructor(
    private val localDataSource: SessionLocalDataSource,
    private val fileExportDataSource: FileExportDataSource
    // private val remoteDataSource: SessionRemoteDataSource // For future cloud sync
) : TypewriterRepository {

    override suspend fun saveSession(session: TypewriterSession): Result<Unit> {
        // Input validation (could also be in UseCase or LocalDataSource)
        if (session.sessionId.isBlank()) {
            return Result.Failure(IllegalArgumentException("Session ID cannot be blank."))
        }
        return localDataSource.saveSession(session)
        // Consider adding remoteDataSource.saveSession(session) for cloud sync
    }

    override suspend fun getSession(sessionId: String): Result<TypewriterSession> {
        if (sessionId.isBlank()) {
            return Result.Failure(IllegalArgumentException("Session ID cannot be blank."))
        }
        // Logic to fetch from local, then remote if not found (or vice-versa)
        return localDataSource.getSession(sessionId)
        // Example:
        // val localResult = localDataSource.getSession(sessionId)
        // if (localResult.succeeded) return localResult
        // val remoteResult = remoteDataSource.getSession(sessionId)
        // if (remoteResult.succeeded) {
        //     remoteResult.data?.let { localDataSource.saveSession(it) } // Cache locally
        // }
        // return remoteResult
    }

    override suspend fun deleteSession(sessionId: String): Result<Unit> {
        if (sessionId.isBlank()) {
            return Result.Failure(IllegalArgumentException("Session ID cannot be blank."))
        }
        return localDataSource.deleteSession(sessionId)
        // Consider adding remoteDataSource.deleteSession(sessionId)
    }

    override fun getSessionList(): Flow<List<TypewriterSession>> {
        // Typically primarily from local data source for speed
        return localDataSource.getSessionList()
        // Could be combined with a remote source that refreshes periodically
    }

    override suspend fun exportAsBitmap(bitmap: Bitmap, title: String): Result<Uri> {
        if (title.isBlank()) {
            return Result.Failure(IllegalArgumentException("Title cannot be blank for export."))
        }
        // Add any other validation for bitmap if needed
        return fileExportDataSource.saveBitmapToDownloads(bitmap, title)
    }

    // suspend fun deleteAllLocalSessions(): Result<Unit> { // Convenience for dev/testing
    //     return localDataSource.deleteAllSessions()
    // }
}

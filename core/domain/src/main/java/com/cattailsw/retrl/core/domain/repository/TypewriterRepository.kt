package com.cattailsw.retrl.core.domain.repository // Updated package

import android.graphics.Bitmap
import android.net.Uri
import com.cattailsw.retrl.core.domain.model.TypewriterSession // Updated import
import com.cattailsw.retrl.core.domain.util.Result // Updated import
import kotlinx.coroutines.flow.Flow

/**
 * Interface for managing typewriter sessions and related data.
 * This abstracts the data sources (local database, cloud storage, etc.).
 */
interface TypewriterRepository {

    /**
     * Saves a complete typewriter session.
     *
     * @param session The session to save.
     * @return Result indicating success or failure.
     */
    suspend fun saveSession(session: TypewriterSession): Result<Unit>

    /**
     * Retrieves a specific typewriter session by its ID.
     *
     * @param sessionId The ID of the session to retrieve.
     * @return Result containing the session if found, or an error.
     */
    suspend fun getSession(sessionId: String): Result<TypewriterSession>

    /**
     * Deletes a typewriter session.
     *
     * @param sessionId The ID of the session to delete.
     * @return Result indicating success or failure.
     */
    suspend fun deleteSession(sessionId: String): Result<Unit>

    /**
     * Gets a list of all typewriter sessions, usually summary information.
     * This might not include the full event list for performance.
     *
     * @return A Flow emitting a list of sessions.
     */
    fun getSessionList(): Flow<List<TypewriterSession>> // Could be summaries

    /**
     * Exports the current canvas content as a Bitmap image to a given Uri.
     *
     * @param bitmap The Bitmap to export.
     * @param title The desired title for the exported file.
     * @return Result containing the Uri of the saved image, or an error.
     */
    suspend fun exportAsBitmap(bitmap: Bitmap, title: String): Result<Uri>

    // Future methods for more complex operations:
    // suspend fun exportSessionAsVideo(sessionId: String, format: VideoFormat): Result<Uri>
    // suspend fun synchronizeWithCloud(session: TypewriterSession): Result<Unit>
    // fun getRealtimeCollaborationChannel(sessionId: String): Flow<CollaborationEvent>
}

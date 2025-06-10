package com.example.domain

import kotlinx.coroutines.flow.Flow

// Placeholder for Bitmap if android.graphics.Bitmap is not available
typealias DomainBitmap = Any
// Placeholder for Uri if android.net.Uri is not available
typealias DomainUri = Any

interface TypewriterRepository {
    // Session Management
    suspend fun saveSession(session: TypewriterSession): Result<Unit>
    suspend fun getSession(id: String): Result<TypewriterSession>
    fun getSessionList(): Flow<List<TypewriterSession>>

    // Exporting
    suspend fun exportAsBitmap(bitmap: DomainBitmap, title: String): Result<DomainUri>
    suspend fun exportAsPdf(pages: List<DomainBitmap>, title: String): Result<DomainUri>
}

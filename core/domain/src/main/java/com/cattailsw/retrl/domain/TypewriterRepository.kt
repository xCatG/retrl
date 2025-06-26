package com.cattailsw.retrl.domain

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface TypewriterRepository {
    suspend fun saveSession(session: TypewriterSession): Result<Unit>
    suspend fun getSession(id: String): Result<TypewriterSession>
    fun getSessionList(): Flow<List<TypewriterSession>>

    suspend fun exportAsBitmap(bitmap: Bitmap, title: String): Result<Uri>
    suspend fun exportAsPdf(pages: List<Bitmap>, title: String): Result<Uri>
}

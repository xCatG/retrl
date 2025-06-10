package com.cattailsw.retrl.core.data.datasource.local // Updated package

import com.cattailsw.retrl.core.domain.model.TypewriterSession // Updated import
import com.cattailsw.retrl.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface SessionLocalDataSource {
    suspend fun saveSession(session: TypewriterSession): Result<Unit>
    suspend fun getSession(sessionId: String): Result<TypewriterSession>
    suspend fun deleteSession(sessionId: String): Result<Unit>
    fun getSessionList(): Flow<List<TypewriterSession>>
    suspend fun deleteAllSessions(): Result<Unit>
}

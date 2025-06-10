package com.example.data

import com.example.domain.TypewriterSession // From :core:domain
import kotlinx.coroutines.flow.Flow

interface SessionLocalDataSource {
    suspend fun saveSession(session: TypewriterSession): Result<Unit>
    suspend fun getSession(id: String): Result<TypewriterSession>
    fun getSessionList(): Flow<List<TypewriterSession>>
    // Potentially add methods like deleteSession, updateSession etc. later
}

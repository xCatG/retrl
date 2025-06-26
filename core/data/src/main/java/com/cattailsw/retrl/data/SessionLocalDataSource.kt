package com.cattailsw.retrl.data

import com.cattailsw.retrl.domain.TypewriterSession
import kotlinx.coroutines.flow.Flow

interface SessionLocalDataSource {
    suspend fun insert(session: TypewriterSession)
    suspend fun get(id: String): TypewriterSession?
    fun getAll(): Flow<List<TypewriterSession>>
}

package com.cattailsw.retrl.core.data.datasource.local // Updated package

import com.cattailsw.retrl.core.data.datasource.local.db.SessionDao // Updated import
import com.cattailsw.retrl.core.data.datasource.local.db.converter.KeystrokeEventListConverter // Updated import
import com.cattailsw.retrl.core.data.datasource.local.db.model.SessionEntity // Updated import
import com.cattailsw.retrl.core.domain.model.TypewriterSession // Updated import
import com.cattailsw.retrl.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomSessionLocalDataSource @Inject constructor(
    private val sessionDao: SessionDao,
    private val keystrokeConverter: KeystrokeEventListConverter // For direct use if needed, though Room handles it
) : SessionLocalDataSource {

    override suspend fun saveSession(session: TypewriterSession): Result<Unit> {
        return try {
            val entity = session.toEntity()
            sessionDao.insertSession(entity)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getSession(sessionId: String): Result<TypewriterSession> {
        return try {
            val entity = sessionDao.getSessionById(sessionId)
            if (entity != null) {
                Result.Success(entity.toDomainModel())
            } else {
                Result.Failure(Exception("Session not found with ID: $sessionId"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun deleteSession(sessionId: String): Result<Unit> {
        return try {
            sessionDao.deleteSessionById(sessionId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override fun getSessionList(): Flow<List<TypewriterSession>> {
        return sessionDao.getAllSessions().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun deleteAllSessions(): Result<Unit> {
        return try {
            sessionDao.deleteAllSessions()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    // Mapper functions
    private fun SessionEntity.toDomainModel(): TypewriterSession {
        return TypewriterSession(
            sessionId = this.sessionId,
            title = this.title,
            creationTimestamp = this.creationTimestamp,
            lastModifiedTimestamp = this.lastModifiedTimestamp,
            events = keystrokeConverter.toKeystrokeEventList(this.eventsJson) ?: emptyList(),
            currentText = this.currentText,
            paperType = this.paperType,
            fontStyle = this.fontStyle,
            inkColor = this.inkColor
        )
    }

    private fun TypewriterSession.toEntity(): SessionEntity {
        return SessionEntity(
            sessionId = this.sessionId,
            title = this.title,
            creationTimestamp = this.creationTimestamp,
            lastModifiedTimestamp = this.lastModifiedTimestamp,
            eventsJson = keystrokeConverter.fromKeystrokeEventList(this.events) ?: "[]",
            currentText = this.currentText,
            paperType = this.paperType,
            fontStyle = this.fontStyle,
            inkColor = this.inkColor
        )
    }
}

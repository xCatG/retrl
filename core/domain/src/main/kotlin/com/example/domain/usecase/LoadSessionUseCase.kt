package com.example.domain.usecase

import com.example.domain.TypewriterRepository
import com.example.domain.TypewriterSession
import javax.inject.Inject

class LoadSessionUseCase @Inject constructor(
    private val repository: TypewriterRepository
) {
    suspend operator fun invoke(sessionId: String): Result<TypewriterSession> {
        // Placeholder implementation
        // return repository.getSession(sessionId)
        // Return a dummy session for placeholder
        val dummySession = TypewriterSession(id = sessionId, title = "Dummy", createdAt = 0L, paperWidthPx = 0, paperHeightPx = 0, events = emptyList())
        return Result.success(dummySession) // Placeholder
    }
}

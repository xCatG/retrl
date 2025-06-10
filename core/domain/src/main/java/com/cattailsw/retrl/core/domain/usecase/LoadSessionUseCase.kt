package com.cattailsw.retrl.core.domain.usecase

import com.cattailsw.retrl.core.domain.model.TypewriterSession
import com.cattailsw.retrl.core.domain.repository.TypewriterRepository
import com.cattailsw.retrl.core.domain.util.Result

class LoadSessionUseCase(private val repository: TypewriterRepository) {
    suspend operator fun invoke(sessionId: String): Result<TypewriterSession> {
        if (sessionId.isBlank()) {
            return Result.Failure(IllegalArgumentException("Session ID cannot be blank"))
        }
        return repository.getSession(sessionId)
    }
}

package com.cattailsw.retrl.core.domain.usecase // Updated package

import com.cattailsw.retrl.core.domain.model.TypewriterSession // Updated import
import com.cattailsw.retrl.core.domain.repository.TypewriterRepository // Updated import
import com.cattailsw.retrl.core.domain.util.Result // Updated import

class SaveSessionUseCase(private val repository: TypewriterRepository) {
    suspend operator fun invoke(session: TypewriterSession): Result<Unit> {
        // Basic validation (can be expanded)
        if (session.title.isBlank()) {
            return Result.Failure(IllegalArgumentException("Session title cannot be blank"))
        }
        if (session.sessionId.isBlank()) {
            return Result.Failure(IllegalArgumentException("Session ID cannot be blank"))
        }
        // Potentially add more validation for events, timestamps, etc.
        return repository.saveSession(session)
    }
}

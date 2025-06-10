package com.example.domain.usecase

import com.example.domain.TypewriterRepository
import com.example.domain.TypewriterSession
import javax.inject.Inject // For Hilt, though :core:domain is pure Kotlin, Hilt can still work with @Inject constructors if used by Hilt modules elsewhere

class SaveSessionUseCase @Inject constructor(
    private val repository: TypewriterRepository
) {
    suspend operator fun invoke(session: TypewriterSession): Result<Unit> {
        // Placeholder implementation
        // return repository.saveSession(session)
        return Result.success(Unit) // Placeholder
    }
}

package com.example.domain.usecase

import com.example.domain.TypewriterRepository
import com.example.domain.TypewriterSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetSessionListUseCase @Inject constructor(
    private val repository: TypewriterRepository
) {
    operator fun invoke(): Flow<List<TypewriterSession>> {
        // Placeholder implementation
        // return repository.getSessionList()
        return flowOf(emptyList()) // Placeholder
    }
}

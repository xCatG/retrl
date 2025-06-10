package com.cattailsw.retrl.core.domain.usecase

import com.cattailsw.retrl.core.domain.model.TypewriterSession
import com.cattailsw.retrl.core.domain.repository.TypewriterRepository
import kotlinx.coroutines.flow.Flow

class GetSessionListUseCase(private val repository: TypewriterRepository) {
    operator fun invoke(): Flow<List<TypewriterSession>> {
        return repository.getSessionList()
    }
}

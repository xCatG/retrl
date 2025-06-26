package com.cattailsw.retrl.domain

class SaveSessionUseCase(private val repository: TypewriterRepository) {
    suspend operator fun invoke(session: TypewriterSession) =
        repository.saveSession(session)
}

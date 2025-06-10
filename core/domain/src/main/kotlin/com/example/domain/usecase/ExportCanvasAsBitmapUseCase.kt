package com.example.domain.usecase

import com.example.domain.DomainBitmap // from :core:domain
import com.example.domain.DomainUri // from :core:domain
import com.example.domain.TypewriterRepository
import javax.inject.Inject

class ExportCanvasAsBitmapUseCase @Inject constructor(
    private val repository: TypewriterRepository
) {
    suspend operator fun invoke(bitmap: DomainBitmap, title: String): Result<DomainUri> {
        // Placeholder implementation
        // return repository.exportAsBitmap(bitmap, title)
        return Result.success(Any() as DomainUri) // Placeholder, Any() casted to DomainUri
    }
}

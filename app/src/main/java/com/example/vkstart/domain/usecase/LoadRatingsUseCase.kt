package com.example.vkstart.domain.usecase

import com.example.vkstart.domain.repository.AppsRepository
import javax.inject.Inject

class LoadRatingsUseCase @Inject constructor(
    private val repository: AppsRepository,
) {
    suspend operator fun invoke(ids: List<String>, parallel: Boolean = false): Map<String, Float> {
        return if (parallel) {
            repository.loadRatingsInParallel(ids)
        } else {
            repository.loadRatingsSequentially(ids)
        }
    }
}

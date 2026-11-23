package com.example.vkstart.domain.usecase

import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import javax.inject.Inject

class GetShowcasePagesUseCase @Inject constructor(
    private val repository: AppsRepository,
) {
    suspend operator fun invoke(pageSize: Int = 3): List<List<AppInfo>> {
        require(pageSize > 0) { "Page size must be positive" }
        return repository.getApps().chunked(pageSize)
    }
}

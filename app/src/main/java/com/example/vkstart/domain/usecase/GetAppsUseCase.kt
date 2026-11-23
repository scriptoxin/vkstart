package com.example.vkstart.domain.usecase

import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val repository: AppsRepository,
) {
    suspend operator fun invoke(): List<AppInfo> {
        return repository.getApps()
    }
}

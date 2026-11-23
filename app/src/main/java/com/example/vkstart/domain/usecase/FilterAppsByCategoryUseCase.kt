package com.example.vkstart.domain.usecase

import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import javax.inject.Inject

class FilterAppsByCategoryUseCase @Inject constructor(
    private val repository: AppsRepository,
) {
    suspend operator fun invoke(category: String?): List<AppInfo> {
        val apps = repository.getApps()
        if (category.isNullOrBlank() || category.equals("Все", ignoreCase = true)) {
            return apps
        }
        return apps.filter { it.category.equals(category, ignoreCase = true) }
    }
}

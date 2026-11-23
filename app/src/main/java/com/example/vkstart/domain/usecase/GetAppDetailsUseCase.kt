package com.example.vkstart.domain.usecase

import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.repository.AppsRepository
import javax.inject.Inject

class GetAppDetailsUseCase @Inject constructor(
    private val repository: AppsRepository,
) {
    suspend operator fun invoke(id: String): AppDetails {
        return repository.getAppDetails(id)
    }
}

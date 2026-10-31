package com.example.vkstart.presentation.appdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AppDetailsUiState(val details: AppDetails, val showcasePages: List<List<AppInfo>>)

class AppDetailsViewModel(appId: Int, repository: AppsRepository) : ViewModel() {
    val uiState = MutableStateFlow(
        AppDetailsUiState(repository.getAppDetails(appId), repository.getApps().chunked(3))
    ).asStateFlow()

    class Factory(private val appId: Int, private val repository: AppsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(AppDetailsViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            return AppDetailsViewModel(appId, repository) as T
        }
    }
}

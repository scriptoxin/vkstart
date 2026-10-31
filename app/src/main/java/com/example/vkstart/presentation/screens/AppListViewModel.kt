package com.example.vkstart.presentation.screens

import androidx.lifecycle.ViewModel
import com.example.vkstart.domain.repository.AppsRepository
import com.example.vkstart.AppContainer
import com.example.vkstart.domain.model.AppInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AppListUiState(val apps: List<AppInfo>)

class AppListViewModel(
    repository: AppsRepository = AppContainer.appsRepository,
) : ViewModel() {
    // Load once per ViewModel, independently of recomposition and Activity recreation.
    val uiState: StateFlow<AppListUiState> =
        MutableStateFlow(AppListUiState(repository.getApps())).asStateFlow()
}

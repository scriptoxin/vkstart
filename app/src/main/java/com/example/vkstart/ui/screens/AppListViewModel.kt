package com.example.vkstart.ui.screens

import androidx.lifecycle.ViewModel
import com.example.vkstart.data.AppsRepository
import com.example.vkstart.data.SampleAppsRepository
import com.example.vkstart.model.AppInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AppListUiState(val apps: List<AppInfo>)

class AppListViewModel(
    repository: AppsRepository = SampleAppsRepository,
) : ViewModel() {
    // Load once per ViewModel, independently of recomposition and Activity recreation.
    val uiState: StateFlow<AppListUiState> =
        MutableStateFlow(AppListUiState(repository.getApps())).asStateFlow()
}

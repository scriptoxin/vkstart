package com.example.vkstart.presentation.screens

import androidx.lifecycle.ViewModel
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class AppListUiState(val apps: List<AppInfo>)

@HiltViewModel
class AppListViewModel @Inject constructor(
    repository: AppsRepository,
) : ViewModel() {
    // Load once per ViewModel, independently of recomposition and Activity recreation.
    val uiState: StateFlow<AppListUiState> =
        MutableStateFlow(AppListUiState(repository.getApps())).asStateFlow()
}

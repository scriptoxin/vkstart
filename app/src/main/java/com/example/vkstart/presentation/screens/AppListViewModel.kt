package com.example.vkstart.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppListUiState(val apps: List<AppInfo>)

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val repository: AppsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppListUiState(emptyList()))
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            val apps = repository.getApps()
            _uiState.value = AppListUiState(apps)
        }
    }
}

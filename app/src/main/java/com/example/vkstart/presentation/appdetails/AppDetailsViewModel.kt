package com.example.vkstart.presentation.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppDetailsUiState(
    val details: AppDetails? = null,
    val showcasePages: List<List<AppInfo>> = emptyList()
)

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppsRepository,
) : ViewModel() {
    private val appId: String = checkNotNull(savedStateHandle["appId"])

    private val _uiState = MutableStateFlow(AppDetailsUiState())
    val uiState: StateFlow<AppDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val details = repository.getAppDetails(appId)
            val apps = repository.getApps().chunked(3)
            _uiState.value = AppDetailsUiState(details, apps)
        }
    }
}

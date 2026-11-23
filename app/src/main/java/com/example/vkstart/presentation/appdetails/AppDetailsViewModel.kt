package com.example.vkstart.presentation.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data class Content(
        val details: AppDetails,
        val showcasePages: List<List<AppInfo>>,
        val installState: InstallState = InstallState.NotInstalled,
    ) : AppDetailsState
}

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppsRepository,
) : ViewModel() {
    private val appId: String = checkNotNull(savedStateHandle["appId"])

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    private var installJob: Job? = null

    init {
        viewModelScope.launch {
            val details = repository.getAppDetails(appId)
            val apps = repository.getApps().chunked(3)
            _state.value = AppDetailsState.Content(details, apps)
        }
    }

    fun onInstallClick() {
        val current = _state.value
        if (current !is AppDetailsState.Content) return

        if (current.installState is InstallState.Installing) {
            installJob?.cancel()
            _state.update {
                if (it is AppDetailsState.Content) {
                    it.copy(installState = InstallState.NotInstalled)
                } else it
            }
        } else if (current.installState is InstallState.NotInstalled) {
            installJob = viewModelScope.launch {
                emulateInstall()
            }
        }
    }

    private suspend fun emulateInstall() {
        for (progress in 0..100 step 5) {
            _state.update { current ->
                if (current is AppDetailsState.Content) {
                    current.copy(installState = InstallState.Installing(progress))
                } else current
            }
            delay(150)
        }
        _state.update { current ->
            if (current is AppDetailsState.Content) {
                current.copy(installState = InstallState.Installed)
            } else current
        }
    }
}

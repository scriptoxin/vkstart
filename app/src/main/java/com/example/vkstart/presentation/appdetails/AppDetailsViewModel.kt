package com.example.vkstart.presentation.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class AppDetailsUiState(val details: AppDetails, val showcasePages: List<List<AppInfo>>)

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: AppsRepository,
) : ViewModel() {
    private val appId: Int = checkNotNull(savedStateHandle["appId"])

    val uiState = MutableStateFlow(
        AppDetailsUiState(repository.getAppDetails(appId), repository.getApps().chunked(3)),
    ).asStateFlow()
}

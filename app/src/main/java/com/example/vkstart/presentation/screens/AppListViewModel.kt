package com.example.vkstart.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import com.example.vkstart.domain.usecase.GetAppsUseCase
import com.example.vkstart.domain.usecase.LoadRatingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

data class AppListUiState(
    val apps: List<AppInfo> = emptyList(),
    val isLoadingRatings: Boolean = false,
    val lastLoadTimeMs: Long? = null,
    val lastLoadType: String? = null,
)

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase,
    private val loadRatingsUseCase: LoadRatingsUseCase,
) : ViewModel() {

    constructor(repository: AppsRepository) : this(
        GetAppsUseCase(repository),
        LoadRatingsUseCase(repository),
    )

    private val _uiState = MutableStateFlow(AppListUiState())
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            val apps = getAppsUseCase()
            _uiState.update { it.copy(apps = apps) }
        }
    }

    fun loadRatingsSequentially() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingRatings = true) }
            val ids = _uiState.value.apps.map { it.id }
            var ratings: Map<String, Float>
            val time = measureTimeMillis {
                ratings = loadRatingsUseCase(ids, parallel = false)
            }
            _uiState.update { state ->
                state.copy(
                    apps = state.apps.map { app -> app.copy(rating = ratings[app.id]) },
                    isLoadingRatings = false,
                    lastLoadTimeMs = time,
                    lastLoadType = "Последовательно",
                )
            }
        }
    }

    fun loadRatingsInParallel() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingRatings = true) }
            val ids = _uiState.value.apps.map { it.id }
            var ratings: Map<String, Float>
            val time = measureTimeMillis {
                ratings = loadRatingsUseCase(ids, parallel = true)
            }
            _uiState.update { state ->
                state.copy(
                    apps = state.apps.map { app -> app.copy(rating = ratings[app.id]) },
                    isLoadingRatings = false,
                    lastLoadTimeMs = time,
                    lastLoadType = "Параллельно",
                )
            }
        }
    }
}

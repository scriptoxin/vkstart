package com.example.vkstart.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vkstart.R
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.presentation.components.AppListItem
import com.example.vkstart.presentation.components.AppToolbar
import com.example.vkstart.presentation.theme.VkstartTheme
import kotlinx.coroutines.launch

@Composable
fun AppListRoute(
    modifier: Modifier = Modifier,
    viewModel: AppListViewModel = hiltViewModel(),
    onAppClick: (AppInfo) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AppListScreen(
        modifier = modifier,
        uiState = uiState,
        onAppClick = onAppClick,
        onLoadSequentiallyClick = { viewModel.loadRatingsSequentially() },
        onLoadInParallelClick = { viewModel.loadRatingsInParallel() },
    )
}

/**
 * Экран списка приложений.
 */
@Composable
fun AppListScreen(
    modifier: Modifier = Modifier,
    uiState: AppListUiState,
    onAppClick: (AppInfo) -> Unit = {},
    onLoadSequentiallyClick: () -> Unit = {},
    onLoadInParallelClick: () -> Unit = {},
) {
    // Transient UI feedback: neither the host nor the click is restored on rotation.
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val toolbarMessage = stringResource(R.string.app_list_toolbar_message)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppToolbar(onClick = {
                scope.launch { snackbarHostState.showSnackbar(toolbarMessage) }
            })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Загрузка рейтингов:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onLoadSequentiallyClick,
                        enabled = !uiState.isLoadingRatings,
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Последовательно",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Button(
                        onClick = onLoadInParallelClick,
                        enabled = !uiState.isLoadingRatings,
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Параллельно",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
                if (uiState.isLoadingRatings) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                if (uiState.lastLoadTimeMs != null && uiState.lastLoadType != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Время (${uiState.lastLoadType}): ${uiState.lastLoadTimeMs} ms",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("app_list"),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = uiState.apps,
                    key = { app -> app.id }
                ) { app ->
                    AppListItem(appInfo = app, onClick = { onAppClick(app) })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    VkstartTheme {
        AppListScreen(uiState = AppListUiState())
    }
}

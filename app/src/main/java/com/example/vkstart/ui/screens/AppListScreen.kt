package com.example.vkstart.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkstart.R
import com.example.vkstart.data.SampleData
import com.example.vkstart.model.AppInfo
import com.example.vkstart.ui.components.AppListItem
import com.example.vkstart.ui.components.AppToolbar
import com.example.vkstart.ui.theme.VkstartTheme
import kotlinx.coroutines.launch

@Composable
fun AppListRoute(
    modifier: Modifier = Modifier,
    viewModel: AppListViewModel = viewModel(),
    onAppClick: (AppInfo) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AppListScreen(modifier = modifier, appList = uiState.apps, onAppClick = onAppClick)
}

/**
 * Экран списка приложений.
 */
@Composable
fun AppListScreen(
    modifier: Modifier = Modifier,
    appList: List<AppInfo>,
    onAppClick: (AppInfo) -> Unit = {}
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("app_list")
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = appList,
                key = { app -> app.id }
            ) { app ->
                AppListItem(appInfo = app, onClick = { onAppClick(app) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    VkstartTheme {
        AppListScreen(appList = SampleData.sampleApps)
    }
}

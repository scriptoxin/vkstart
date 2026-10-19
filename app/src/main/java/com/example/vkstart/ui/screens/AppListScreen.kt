package com.example.vkstart.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vkstart.data.SampleData
import com.example.vkstart.model.AppInfo
import com.example.vkstart.ui.components.AppListItem
import com.example.vkstart.ui.components.AppToolbar
import com.example.vkstart.ui.theme.VkstartTheme

/**
 * Экран списка приложений.
 */
@Composable
fun AppListScreen(
    modifier: Modifier = Modifier,
    appList: List<AppInfo> = SampleData.sampleApps
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppToolbar()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = appList,
                key = { app -> app.id }
            ) { app ->
                AppListItem(appInfo = app)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    VkstartTheme {
        AppListScreen()
    }
}

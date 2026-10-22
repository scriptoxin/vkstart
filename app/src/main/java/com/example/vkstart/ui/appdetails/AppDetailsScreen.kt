package com.example.vkstart.ui.appdetails

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.vkstart.R
import com.example.vkstart.model.AppDetails
import com.example.vkstart.model.AppInfo
import kotlinx.coroutines.launch

/** VkEducation's detail screen, using hardcoded data instead of Hilt/API state. */
@Composable
fun AppDetailsScreen(
    appDetails: AppDetails,
    onBackClick: () -> Unit,
    onShowcaseAppClick: (AppInfo) -> Unit,
) {
    var descriptionExpanded by rememberSaveable(appDetails.id) { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val underDevelopment = stringResource(R.string.under_development)
    val showUnderDevelopment: () -> Unit = {
        scope.launch { snackbarHostState.showSnackbar(underDevelopment) }
        Unit
    }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        AppDetailsContent(
            appDetails = appDetails,
            descriptionExpanded = descriptionExpanded,
            onBackClick = onBackClick,
            onShareClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "${appDetails.name}\n${appDetails.description}")
                }
                context.startActivity(Intent.createChooser(intent, appDetails.name))
            },
            onInstallClick = showUnderDevelopment,
            onReadMoreClick = { descriptionExpanded = true },
            onDeveloperClick = showUnderDevelopment,
            onShowcaseAppClick = onShowcaseAppClick,
            modifier = Modifier.fillMaxSize().padding(padding),
        )
    }
}

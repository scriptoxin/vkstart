package com.example.vkstart.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vkstart.AppContainer
import com.example.vkstart.presentation.appdetails.AppDetailsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import com.example.vkstart.presentation.appdetails.AppDetailsScreen
import com.example.vkstart.presentation.screens.AppListRoute

private const val APP_LIST = "apps"
private const val APP_DETAILS = "apps/{appId}"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = APP_LIST) {
        composable(APP_LIST) {
            AppListRoute(onAppClick = { app ->
                navController.navigate("apps/${app.id}") { launchSingleTop = true }
            })
        }
        composable(
            route = APP_DETAILS,
            arguments = listOf(navArgument("appId") { type = NavType.IntType }),
        ) { entry ->
            val appId = requireNotNull(entry.arguments).getInt("appId")
            val detailsViewModel: AppDetailsViewModel = viewModel(
                viewModelStoreOwner = entry,
                factory = AppDetailsViewModel.Factory(appId, AppContainer.appsRepository),
            )
            val state by detailsViewModel.uiState.collectAsStateWithLifecycle()
            AppDetailsScreen(
                appDetails = state.details,
                showcasePages = state.showcasePages,
                onBackClick = { navController.popBackStack() },
                onShowcaseAppClick = {
                    navController.navigate(APP_LIST) {
                        popUpTo(APP_LIST) { inclusive = false }
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

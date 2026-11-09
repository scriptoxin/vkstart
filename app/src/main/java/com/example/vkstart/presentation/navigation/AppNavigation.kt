package com.example.vkstart.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vkstart.presentation.appdetails.AppDetailsScreen
import com.example.vkstart.presentation.appdetails.AppDetailsViewModel
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
            arguments = listOf(navArgument("appId") { type = NavType.StringType }),
        ) {
            val detailsViewModel: AppDetailsViewModel = hiltViewModel()
            val state by detailsViewModel.uiState.collectAsStateWithLifecycle()
            state.details?.let { details ->
                AppDetailsScreen(
                    appDetails = details,
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
}

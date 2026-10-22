package com.example.vkstart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vkstart.data.SampleData
import com.example.vkstart.ui.appdetails.AppDetailsScreen
import com.example.vkstart.ui.screens.AppListScreen

private const val APP_LIST = "apps"
private const val APP_DETAILS = "apps/{appId}"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = APP_LIST) {
        composable(APP_LIST) {
            AppListScreen(onAppClick = { app ->
                navController.navigate("apps/${app.id}") { launchSingleTop = true }
            })
        }
        composable(
            route = APP_DETAILS,
            arguments = listOf(navArgument("appId") { type = NavType.IntType }),
        ) { entry ->
            val appId = requireNotNull(entry.arguments).getInt("appId")
            AppDetailsScreen(
                appDetails = SampleData.detailsFor(appId),
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

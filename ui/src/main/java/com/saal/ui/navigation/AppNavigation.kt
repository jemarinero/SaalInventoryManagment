package com.saal.ui.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saal.ui.feature.home.HomeScreen
import com.saal.ui.feature.item_detail.ItemDetailScreen
import com.saal.ui.feature.splash.SplashScreen
import com.saal.ui.theme.SystemUiBarColor

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    SystemUiBarColor(darkIcons = isSystemInDarkTheme())
    Box {
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Splash.route
        ) {
            composable(NavigationItem.Splash.route) {

                SplashScreen {
                    navController.navigate(NavigationItem.Home.route) {
                        popUpTo(route = NavigationItem.Splash.route) { inclusive = true }
                    }
                }
            }
            composable(NavigationItem.Home.route) {
                HomeScreen { itemId ->
                    navController.navigate("${NavigationItem.Detail.route}/${itemId ?: 0L}") {
                        popUpTo(route = NavigationItem.Home.route )
                    }
                }
            }

            composable(
                route = "${NavigationItem.Detail.route}/{itemId}",
                arguments = listOf(
                    navArgument("itemId") {
                        type = NavType.LongType
                        defaultValue = 0L
                    }
                )
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getLong("itemId")
                ItemDetailScreen(
                    itemId,
                    onNavigateBack = { navController.navigateUp() },
                    onMenuClick = {}
                )
            }
        }
    }
}

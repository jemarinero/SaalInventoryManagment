package com.saal.ui.navigation

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.SPLASH.name)
    data object Home : NavigationItem(Screen.HOME.name)
    data object Detail : NavigationItem(Screen.DETAIL.name)
}

enum class Screen {
    SPLASH,
    HOME,
    DETAIL
}
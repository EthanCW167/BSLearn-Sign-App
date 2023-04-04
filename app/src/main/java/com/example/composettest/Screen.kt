package com.example.composettest

sealed class Screen(val route: String) {
    object HomeScreen : Screen("main_screen")
    object Dictionary : Screen("dictionary")

}

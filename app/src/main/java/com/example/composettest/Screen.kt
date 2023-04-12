package com.example.composettest

sealed class Screen(val route: String) {
    object HomeScreen : Screen("main_screen")
    object Dictionary : Screen("dictionary")
    object SignView : Screen("sign-view")

    fun withArgs(vararg args: Any): String {
        return buildString() {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}

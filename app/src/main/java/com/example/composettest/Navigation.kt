package com.example.composettest


import SignView
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composettest.UserInterface.Dictionary
import com.example.composettest.UserInterface.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Dictionary.route){
            Dictionary(navController = navController)
        }
        composable(
            route = Screen.SignView.route + "/{filepath}/{sign}",
            arguments = listOf(
                navArgument("filepath") {
                    type = NavType.IntType
                    defaultValue = R.raw.sample1
                    nullable = false},

                navArgument("sign") {
                    type = NavType.StringType
                    defaultValue = R.raw.sample1
                    nullable = false
                }
            )
        ) { entry ->
            val filePath = entry.arguments?.getInt("filepath")

            val sign = entry.arguments?.getString("sign")

                    if (filePath != null) {
                        if (sign != null) {
                            SignView(navController = navController, filepath = filePath, sign = sign)
                        }
                }
            }
    }
}
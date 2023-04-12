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
/*
abstract class JsonNavType<T> : NavType<T>(isNullableAllowed = false) {
    abstract fun fromJsonParse(value: String): T
    abstract fun T.getJsonParse(): String

    override fun get(bundle: Bundle, key: String): T? =
        bundle.getString(key)?.let { parseValue(it) }

    override fun parseValue(value: String): T = fromJsonParse(value)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, value.getJsonParse())
    }
}

class ProfileArgType : JsonNavType<LessonData>() {
    override fun fromJsonParse(value: String): LessonData = Gson().fromJson(value, LessonData::class.java)

    override fun FromProfileArguments.getJsonParse(): String = Gson().toJson(this)
    }
 */

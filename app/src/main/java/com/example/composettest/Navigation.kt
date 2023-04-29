package com.example.composettest


import SignView
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composettest.Lesson.LessonPreviewScreen
import com.example.composettest.Lesson.LessonQuestionMultiChoiceScreen
import com.example.composettest.Lesson.LessonSignViewScreen
import com.example.composettest.Lesson.LessonSummaryScreen
import com.example.composettest.Lesson.LessonTest.LessonTest
import com.example.composettest.LessonMaker.LessonMakerEditQuestionsScreen
import com.example.composettest.LessonMaker.LessonMakerEditScreen
import com.example.composettest.LessonMaker.LessonMakerOverview
import com.example.composettest.LessonMaker.LessonShareScreen
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
            route = Screen.SignView.route + "?filePath={filepath}&sign={sign}",
            arguments = listOf(
                navArgument("filepath") {
                    type = NavType.IntType
                    defaultValue = R.raw.sample1
                    nullable = false},

                navArgument("sign") {
                    type = NavType.StringType
                    defaultValue = "hello"
                    nullable = false
                }
            )
        ) {
            val filePath = it.arguments?.getInt("filepath")
            val sign = it.arguments?.getString("sign")

            if (filePath != null) {
                if (sign != null) {
                    SignView(navController = navController, filepath = filePath, sign = sign)
                        }

                }
            }
        composable(route = Screen.LessonPreviewScreen.route + "?id={id}",
            arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
                defaultValue = 0
            }
        )) {
            val lessonId = it.arguments?.getInt("id") ?: 0
            LessonPreviewScreen(navController = navController, id = lessonId)
        }
        composable(route = Screen.LessonSignViewScreen.route + "?orderNum={orderNum}&lessonId={lessonId}&numQuestions={numQuestions}",
        arguments = listOf(
            navArgument("orderNum"){
                type = NavType.IntType
                defaultValue = 1
            },
            navArgument("lessonId"){
                type = NavType.IntType
                defaultValue = 0
            },
            navArgument("numQuestion"){
                type = NavType.IntType
                defaultValue = 1
            }

        )) {
            val orderNum = it.arguments?.getInt("orderNum") ?: 1
            val lessonId = it.arguments?.getInt("lessonId") ?: 0
            val numQuestion =  it.arguments?.getInt("numQuestion") ?: 1
            LessonSignViewScreen(navController = navController, orderNum = orderNum, lessonId = lessonId, numQuestion = numQuestion)
        }
        composable(route = Screen.LessonTest.route + "?lessonId={lessonId}&orderNum={orderNum}",
            arguments = listOf(
                navArgument("lessonId"){
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("orderNum"){
                    type = NavType.IntType
                    defaultValue = 1
                }
            )
        ){
            val lessonId = it.arguments?.getInt("lessonId") ?: 0
            val orderNum = it.arguments?.getInt("orderNum") ?: 1
            LessonTest(navController = navController, questionId = lessonId, orderNum = orderNum)
        }
        composable(route = Screen.LessonQuestionMultiChoiceScreen.route + "?lessonId={lessonId}&orderNum={orderNum}&numQuestions={numQuestions}",
            arguments = listOf(
                navArgument("lessonId"){
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("orderNum"){
                    type = NavType.IntType
                    defaultValue = 1
                },
                navArgument("numQuestion"){
                    type = NavType.IntType
                    defaultValue = 1
                }
            )
        ){
            val lessonId = it.arguments?.getInt("lessonId") ?: 0
            val orderNum = it.arguments?.getInt("orderNum") ?: 1
            val numQuestion =  it.arguments?.getInt("numQuestion") ?: 1
            LessonQuestionMultiChoiceScreen(navController = navController, orderNum = orderNum, lessonId = lessonId, numQuestion = numQuestion)
        }
        composable(route = Screen.LessonSummaryScreen.route + "?lessonId={lessonId}",
            arguments = listOf(
                navArgument("lessonId"){
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){
            val lessonId = it.arguments?.getInt("lessonId") ?: 0
            LessonSummaryScreen(navController = navController, lessonId = lessonId)
        }
        composable(route = Screen.LessonMakerOverview.route + "?userId={userId}",
            arguments = listOf(
                navArgument("userId"){
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ){
            val userId = it.arguments?.getString("userId") ?: "-1"
            LessonMakerOverview(navController = navController, userId = userId)
        }
        composable(route = Screen.LessonMakerEditScreen.route + "?lessonId={lessonId}&questionIndex={questionIndex}&userId={userId}",
            arguments = listOf(
                navArgument("lessonId"){
                    type = NavType.StringType
                    defaultValue = "1"
                },
                navArgument("questionIndex"){
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("userId"){
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ){
            val lessonId = it.arguments?.getString("lessonId") ?: "1"
            val questionIndex = it.arguments?.getInt("questionIndex") ?: -1
            val userId = it.arguments?.getString("userId") ?: "-1"
            LessonMakerEditScreen(navController = navController, lessonId = lessonId, userId = userId)
        }
        composable(route = Screen.LessonMakerEditQuestionScreen.route + "?lessonId={lessonId}&questionIndex={questionIndex}&userId={userId}",
            arguments = listOf(
                navArgument("lessonId"){
                    type = NavType.StringType
                    defaultValue = "1"
                },
                navArgument("questionIndex"){
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("userId"){
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ){
            val lessonId = it.arguments?.getString("lessonId") ?: "1"
            val questionIndex = it.arguments?.getInt("questionIndex") ?: -1
            val userId = it.arguments?.getString("userId") ?: "-1"
            LessonMakerEditQuestionsScreen(navController = navController, lessonId = lessonId, questionIndex = questionIndex, userId = userId)
        }
        composable(route = Screen.LessonShareScreen.route + "?lessonId={lessonId}",
            arguments = listOf(
                navArgument("lessonId"){
                    type = NavType.StringType
                    defaultValue = "1"
                }
            )


        ){
            val lessonId = it.arguments?.getString("lessonId") ?: "1"
            LessonShareScreen(navController = navController, lessonId = lessonId)
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

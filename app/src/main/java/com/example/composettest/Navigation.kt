package com.example.composettest


import SignView
import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composettest.Lesson.*
import com.example.composettest.Lesson.LessonTest.LessonTest
import com.example.composettest.LessonMaker.LessonMakerEditQuestionsScreen
import com.example.composettest.LessonMaker.LessonMakerEditScreen
import com.example.composettest.LessonMaker.LessonMakerOverview
import com.example.composettest.LessonMaker.LessonShareScreen
import com.example.composettest.LessonMaker.PreviewLesson.LessonMakerPreviewMultiChoiceScreen
import com.example.composettest.LessonMaker.PreviewLesson.LessonMakerPreviewSignViewScreen
import com.example.composettest.Practice.PracticeMultiChoice
import com.example.composettest.Practice.PracticeSelectionScreen
import com.example.composettest.Practice.PracticeSignView
import com.example.composettest.Practice.PracticeSummaryScreen
import com.example.composettest.SharedLessons.*
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
        composable(route = Screen.PopScreen.route) {
            PopScreen(navController = navController)
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
        composable(route = Screen.LessonSignViewScreen.route + "?orderNum={orderNum}&lessonId={lessonId}&numQuestion={numQuestion}&lessonTitle={lessonTitle}",
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
            },
            navArgument("lessonTitle"){
                type = NavType.StringType
                defaultValue = "Lesson"
            }

        )) {
            val orderNum = it.arguments?.getInt("orderNum") ?: 1
            val lessonId = it.arguments?.getInt("lessonId") ?: 0
            val numQuestion =  it.arguments?.getInt("numQuestion") ?: 1
            val lessonTitle = it.arguments?.getString("lessonTitle") ?: "Lesson"
            LessonSignViewScreen(navController = navController, orderNum = orderNum, lessonId = lessonId, numQuestion = numQuestion, lessonTitle = lessonTitle)
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
        composable(route = Screen.LessonQuestionMultiChoiceScreen.route + "?lessonId={lessonId}&orderNum={orderNum}&numQuestion={numQuestion}&lessonTitle={lessonTitle}",
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
                },
                navArgument("lessonTitle"){
                    type = NavType.StringType
                    defaultValue = "Lesson"
                }
            )
        ){
            val lessonId = it.arguments?.getInt("lessonId") ?: 0
            val orderNum = it.arguments?.getInt("orderNum") ?: 1
            val numQuestion =  it.arguments?.getInt("numQuestion") ?: 1
            val lessonTitle = it.arguments?.getString("lessonTitle") ?: "Lesson"
            LessonQuestionMultiChoiceScreen(navController = navController, orderNum = orderNum, lessonId = lessonId, numQuestion = numQuestion, lessonTitle = lessonTitle)
        }
        composable(route = Screen.LessonSummaryScreen.route + "?lessonId={lessonId}&lessonTitle={lessonTitle}",
            arguments = listOf(
                navArgument("lessonId"){
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("lessonTitle"){
                    type = NavType.StringType
                    defaultValue = "Lesson"
                }
            )
        ){
            val lessonId = it.arguments?.getInt("lessonId") ?: 0
            val lessonTitle = it.arguments?.getString("lessonTitle") ?: "Lesson"
            LessonSummaryScreen(navController = navController, lessonId = lessonId, lessonTitle = lessonTitle)
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
        composable(route = Screen.PracticeSelectionScreen.route){
            PracticeSelectionScreen(navController = navController)
        }
        composable(route = Screen.PracticeSignView.route + "?questionIndex={questionIndex}",
            arguments = listOf(
                navArgument("questionIndex"){
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){
            val questionIndex = it.arguments?.getInt("questionIndex") ?: 0
            PracticeSignView(navController = navController, questionIndex = questionIndex)
        }
        composable(route = Screen.PracticeMultiChoice.route + "?questionIndex={questionIndex}",
            arguments = listOf(
                navArgument("questionIndex"){
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){
            val questionIndex = it.arguments?.getInt("questionIndex") ?: 0
            PracticeMultiChoice(navController = navController, questionIndex = questionIndex)
        }
        composable(route = Screen.PracticeSummaryScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument("userId"){
                    type = NavType.StringType
                    defaultValue = "-1"
                }
        )){
            val userId = it.arguments?.getString("userId") ?: "-1"
            PracticeSummaryScreen(navController = navController, userId = userId)
        }
        composable(route = Screen.LessonMakerPreviewSignViewScreen.route + "?questionIndex={questionIndex}",
            arguments = listOf(
                navArgument("questionIndex"){
                    type = NavType.IntType
                    defaultValue = 0
                }
                )
        ){
            val questionIndex = it.arguments?.getInt("questionIndex") ?: 0
            LessonMakerPreviewSignViewScreen(navController = navController, questionIndex = questionIndex)


         }
        composable(route = Screen.LessonMakerPreviewMultiChoiceScreen.route + "?questionIndex={questionIndex}",
            arguments = listOf(
                navArgument("questionIndex"){
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){
            val questionIndex = it.arguments?.getInt("questionIndex") ?: 0
            LessonMakerPreviewMultiChoiceScreen(navController = navController, questionIndex = questionIndex)


        }
        composable(route = Screen.SharedLessonsScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument("userId"){
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ){
            val userId = it.arguments?.getString("userId") ?: "-1"
            SharedLessonsScreen(navController = navController, userId = userId)
        }
        composable(route = Screen.SharedLessonSignViewScreen.route + "?lessonId={lessonId}&questionIndex={questionIndex}&lessonTitle={lessonTitle}",
            arguments = listOf(
                navArgument("lessonId"){
                    type = NavType.StringType
                    defaultValue = "-1"
                },
                navArgument("questionIndex"){
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("lessonTitle"){
                    type = NavType.StringType
                    defaultValue = "Lesson"
                }
            )
        ){
            val lessonId = it.arguments?.getString("lessonId") ?: "-1"
            val questionIndex = it.arguments?.getInt("questionIndex") ?: 0
            val lessonTitle = it.arguments?.getString("lessonTitle") ?: "Lesson"
            SharedLessonSignViewScreen(
                navController = navController,
                lessonId = lessonId,
                questionIndex = questionIndex,
                lessonTitle = lessonTitle
            )
        }
        composable(route = Screen.SharedLessonMultiChoiceScreen.route + "?lessonId={lessonId}&questionIndex={questionIndex}&lessonTitle={lessonTitle}",
            arguments = listOf(
                navArgument("lessonId"){
                    type = NavType.StringType
                    defaultValue = "-1"
                },
                navArgument("questionIndex"){
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("lessonTitle"){
                    type = NavType.StringType
                    defaultValue = "Lesson"
                }
            )
        ){
            val lessonId = it.arguments?.getString("lessonId") ?: "-1"
            val questionIndex = it.arguments?.getInt("questionIndex") ?: 0
            val lessonTitle = it.arguments?.getString("lessonTitle") ?: "Lesson"
            SharedLessonMultiChoiceScreen(
                navController = navController,
                lessonId = lessonId,
                questionIndex = questionIndex,
                lessonTitle = lessonTitle
            )
        }
        composable(route = Screen.SharedLessonSummaryScreen.route + "?lessonTitle={lessonTitle}",
            arguments = listOf(
                navArgument("lessonTitle"){
                    type = NavType.StringType
                    defaultValue = "Lesson"
                }
            )
        ){
            val lessonTitle = it.arguments?.getString("lessonTitle") ?: "Lesson"
            SharedLessonSummaryScreen(navController = navController, lessonTitle = lessonTitle)
        }
        composable(route = Screen.SharedLessonPreviewScreen.route + "?lessonId={lessonId}",
            arguments = listOf(
                navArgument("lessonId"){
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ){
            val lessonId = it.arguments?.getString("lessonId") ?: "-1"
            SharedLessonPreviewScreen(navController = navController, lessonId = lessonId)
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

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

    object LessonPreviewScreen : Screen("lesson_preview")

    object LessonSignViewScreen : Screen("lesson_sign_view")

    object LessonTest: Screen("lesson_test")

    object LessonQuestionMultiChoiceScreen: Screen("lesson_multi_choice")

    object LessonSummaryScreen: Screen("lesson_summary")

    object LessonMakerOverview: Screen("lesson_maker_overview")

    object LessonMakerEditScreen: Screen("lesson_maker_edit")
}

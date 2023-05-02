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

    object LessonMakerEditQuestionScreen: Screen("lesson_maker_question_edit")

    object LessonShareScreen: Screen("lesson_share")

    object PracticeSelectionScreen: Screen("practice_selection")

    object PracticeSignView: Screen("practice_sign_view")

    object PracticeMultiChoice: Screen("practice_multi_choice")

    object PracticeSummaryScreen: Screen("practice_summary")

    object LessonMakerPreviewSignViewScreen: Screen("preview_sign_view")

    object LessonMakerPreviewMultiChoiceScreen: Screen("preview_multi_choice")

    object SharedLessonsScreen: Screen("shared_lessons")

    object SharedLessonSignViewScreen: Screen("shared_sign_view")

    object SharedLessonMultiChoiceScreen: Screen("shared_multi_choice")

    object SharedLessonSummaryScreen: Screen("shared_summary")

    object SharedLessonPreviewScreen: Screen("shared_preview")
}


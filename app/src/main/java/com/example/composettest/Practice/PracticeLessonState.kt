package com.example.composettest.Practice

import com.example.composettest.Domain.model.FLesson

data class PracticeLessonState(
    var lesson: FLesson = FLesson(
        name = "Practice",
        signs = 0,
        questions = 0,
        questionsList = emptyList()
    )
)

package com.example.composettest.LessonMaker

import com.example.composettest.Domain.model.FLesson

data class LessonMakerState(
    val lessons: List<FLesson> = emptyList()
)

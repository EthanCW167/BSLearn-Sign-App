package com.example.composettest.Lesson

import com.example.composettest.Domain.model.Question
import kotlinx.coroutines.flow.Flow

data class QuestionState(
    val questions: List<Question> = emptyList()
)

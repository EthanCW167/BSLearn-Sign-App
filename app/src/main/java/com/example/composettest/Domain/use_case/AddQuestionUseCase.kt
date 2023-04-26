package com.example.composettest.Domain.use_case

import com.example.composettest.Domain.model.Question
import com.example.composettest.Domain.repository.LessonRepository

class AddQuestionUseCase (
    private val repository: LessonRepository
        ) {

    suspend operator fun invoke(question: Question){
        repository.insertQuestion(question)
    }
}
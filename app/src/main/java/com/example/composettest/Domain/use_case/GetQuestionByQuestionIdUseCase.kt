package com.example.composettest.Domain.use_case

import com.example.composettest.Domain.model.Question
import com.example.composettest.Domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow

class GetQuestionByQuestionIdUseCase (
    private val repository: LessonRepository
){
    operator fun invoke(questionId: Int): Flow<List<Question>> {
        return repository.getQuestionsByQuestionId(questionId)
    }
}
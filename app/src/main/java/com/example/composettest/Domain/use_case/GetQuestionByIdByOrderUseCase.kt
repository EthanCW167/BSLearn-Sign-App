package com.example.composettest.Domain.use_case

import com.example.composettest.Domain.model.Question
import com.example.composettest.Domain.repository.LessonRepository
import com.example.composettest.Domain.util.QuestionOrder
import kotlinx.coroutines.flow.Flow

class GetQuestionByIdByOrderUseCase (
    private val repository: LessonRepository
){
    suspend operator fun invoke(questionId: Int, orderNum: Int): Question?{
        return repository.getQuestionByIdByOrder(questionId, orderNum)
    }
}
package com.example.composettest.Lesson

import com.example.composettest.Domain.model.Question
import com.example.composettest.Domain.util.OrderType
import com.example.composettest.Domain.util.QuestionOrder

data class QuestionState(
    val questions: List<Question> = emptyList(),
    val questionOrder: QuestionOrder = QuestionOrder.orderNum(OrderType.Descending)
)

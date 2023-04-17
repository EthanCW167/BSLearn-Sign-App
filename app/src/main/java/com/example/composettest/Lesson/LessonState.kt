package com.example.composettest.Lesson

import com.example.composettest.Domain.model.Lesson
import com.example.composettest.Domain.util.LessonOrder
import com.example.composettest.Domain.util.OrderType

data class LessonState(
    val lessons: List<Lesson> = emptyList(),
    val lessonOrder: LessonOrder = LessonOrder.LessonNum(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)

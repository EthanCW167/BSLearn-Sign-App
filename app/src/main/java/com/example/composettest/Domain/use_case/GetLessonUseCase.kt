package com.example.composettest.Domain.use_case

import com.example.composettest.Domain.model.Lesson
import com.example.composettest.Domain.repository.LessonRepository
import com.example.composettest.Domain.util.LessonOrder
import com.example.composettest.Domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLessonUseCase(
    private val repository: LessonRepository
) {

    operator fun invoke(
        lessonOrder: LessonOrder = LessonOrder.LessonNum(OrderType.Descending)
    ): Flow<List<Lesson>> {
        return repository.getLesson().map { lessons ->
            when(lessonOrder.orderType) {
                is OrderType.Ascending -> {
                    when(lessonOrder) {
                        is LessonOrder.LessonNum -> lessons.sortedBy {it.lessonNum}
                            is LessonOrder.Name -> lessons.sortedBy { it.name.lowercase() }
                    }
                }
                is OrderType.Descending -> {
                    when(lessonOrder) {
                        is LessonOrder.LessonNum -> lessons.sortedByDescending {it.lessonNum}
                        is LessonOrder.Name -> lessons.sortedByDescending { it.name.lowercase() }
                    }
                }
        }
        }
    }
}
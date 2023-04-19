package com.example.composettest.Domain.use_case

data class LessonUseCases(
    val getLessonsUseCase: GetLessonsUseCase,
    val deleteLessonUseCase: DeleteLessonUseCase,
    val getLessonUseCase: GetLessonUseCase,
    val getQuestionsByIdUseCase: GetQuestionsByIdUseCase,
    val getSignDataByIdUseCase: GetSignDataByIdUseCase,
    val getQuestionByQuestionIdUseCase: GetQuestionByQuestionIdUseCase,
    val getQuestionByIdByOrderUseCase: GetQuestionByIdByOrderUseCase
)

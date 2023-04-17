package com.example.composettest.Domain.use_case

import com.example.composettest.Domain.model.Lesson
import com.example.composettest.Domain.repository.LessonRepository

class GetLessonUseCase(
    private val repository: LessonRepository
    ){

    suspend operator fun invoke(id: Int): Lesson? {
        return repository.getLessonById(id)
    }
}
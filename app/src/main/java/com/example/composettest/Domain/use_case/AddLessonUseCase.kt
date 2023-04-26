package com.example.composettest.Domain.use_case

import com.example.composettest.Domain.model.Lesson
import com.example.composettest.Domain.model.Question
import com.example.composettest.Domain.repository.LessonRepository

class AddLessonUseCase(
    private val repository: LessonRepository
) {

    suspend operator fun invoke(lesson: Lesson){
        repository.insertLesson(lesson)
    }
}
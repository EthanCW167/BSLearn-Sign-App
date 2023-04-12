package com.example.composettest.Domain.repository

import androidx.room.Delete
import com.example.composettest.Domain.model.Lesson
import kotlinx.coroutines.flow.Flow

interface LessonRepository {

    fun getLesson(): Flow<List<Lesson>>

    suspend fun getLessonById(id: Int): Lesson?

    suspend fun insertLesson(lesson: Lesson)

    suspend fun deleteLesson(lesson: Lesson)
}
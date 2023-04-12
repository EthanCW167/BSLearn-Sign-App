package com.example.composettest.Data.repository

import com.example.composettest.Data.data_source.LessonDao
import com.example.composettest.Domain.model.Lesson
import com.example.composettest.Domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow

class LessonRepositoryImpl(
    private val dao: LessonDao
) : LessonRepository{

    override fun getLesson(): Flow<List<Lesson>> {
        return dao.getLessons()
    }

    override suspend fun getLessonById(id: Int): Lesson? {
        return dao.getLessonById(id)
    }

    override suspend fun insertLesson(lesson: Lesson) {
        dao.insertLesson(lesson)
    }

    override suspend fun deleteLesson(lesson: Lesson) {
        dao.deleteLesson(lesson)
    }
}
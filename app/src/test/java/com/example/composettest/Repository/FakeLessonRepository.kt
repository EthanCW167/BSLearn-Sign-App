package com.example.composettest.Repository

import com.example.composettest.Domain.model.*
import com.example.composettest.Domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLessonRepository: LessonRepository {

    private val lessons = mutableListOf<Lesson>()

    override fun getLessons(): Flow<List<Lesson>> {
        return flow { emit(lessons) }
    }

    override suspend fun getLessonById(id: Int): Lesson? {
        TODO("Not yet implemented")
    }

    override suspend fun insertLesson(lesson: Lesson) {
        lessons.add(lesson)
    }

    override suspend fun deleteLesson(lesson: Lesson) {
        TODO("Not yet implemented")
    }

    override fun getLessonsWithQuestions(): Flow<List<LessonQuestions>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLessonByIdWithQuestions(id: Int): LessonQuestions? {
        TODO("Not yet implemented")
    }

    override suspend fun getQuestionByIdWithSignData(lessonId: Int): Flow<List<QuestionSignData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getQuestionByIdByOrder(lessonId: Int, orderNum: Int): Question? {
        TODO("Not yet implemented")
    }

    override suspend fun getQuestionBylessonIdByOrderNumWithSignData(
        lessonId: Int,
        orderNum: Int
    ): QuestionSignData? {
        TODO("Not yet implemented")
    }

    override fun getQuestionsByLessonId(lessonId: Int): Flow<List<Question>> {
        TODO("Not yet implemented")
    }

    override fun getQuestionsByQuestionId(questionId: Int): Flow<List<Question>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSignDataBySignId(signId: Int): signData? {
        TODO("Not yet implemented")
    }

    override suspend fun insertQuestion(question: Question) {
        TODO("Not yet implemented")
    }
}
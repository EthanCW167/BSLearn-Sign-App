package com.example.composettest.Data.repository

import com.example.composettest.Data.data_source.LessonDao
import com.example.composettest.Domain.model.*
import com.example.composettest.Domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow

class LessonRepositoryImpl(
    private val dao: LessonDao
) : LessonRepository {

    override fun getLessons(): Flow<List<Lesson>> {
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

    override fun getLessonsWithQuestions(): Flow<List<LessonQuestions>> {
        return dao.getLessonsWithQuestions()
    }

    override suspend fun getLessonByIdWithQuestions(id: Int): LessonQuestions? {
        return dao.getLessonByIdWithQuestions(id)
    }

    override suspend fun getQuestionByIdWithSignData(questionId: Int): Flow<List<QuestionSignData>> {
        return dao.getQuestionByIdWithSignData(questionId)
    }

    override suspend fun getQuestionByIdByOrder(lessonId: Int, orderNum: Int): Question? {
        return dao.getQuestionsByIdByOrder(lessonId, orderNum)
    }

    override suspend fun getQuestionBylessonIdByOrderNumWithSignData(lessonId: Int, orderNum: Int): QuestionSignData? {
        return dao.getQuestionBylessonIdByOrderNumWithSignData(lessonId, orderNum)
    }

    override fun getQuestionsByLessonId(lessonId: Int): Flow<List<Question>> {
        return dao.getQuestionsByLessonId(lessonId)
    }

    override fun getQuestionsByQuestionId(questionId: Int): Flow<List<Question>> {
        return dao.getQuestionsByQuestionId(questionId)
    }

    override suspend fun getSignDataBySignId(signId: Int): signData? {
        return dao.getSignDataBySignId(signId)
    }

//    override suspend fun collect(id: Int, qId: Int): Collect? {
//        return dao.collect(id, qId)
//    }

    override suspend fun insertQuestion(question: Question){
        return dao.insertQuestion(question)
    }

}
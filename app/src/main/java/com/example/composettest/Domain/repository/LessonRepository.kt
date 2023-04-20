package com.example.composettest.Domain.repository

import androidx.room.Delete
import com.example.composettest.Domain.model.*
import kotlinx.coroutines.flow.Flow

interface LessonRepository {

    fun getLessons(): Flow<List<Lesson>>

    suspend fun getLessonById(id: Int): Lesson?

    suspend fun insertLesson(lesson: Lesson)

    suspend fun deleteLesson(lesson: Lesson)

    fun getLessonsWithQuestions(): Flow<List<LessonQuestions>>

    suspend fun getLessonByIdWithQuestions(id: Int): LessonQuestions?

    suspend fun getQuestionByIdWithSignData(lessonId: Int): Flow<List<QuestionSignData>>

    suspend fun getQuestionByIdByOrder(lessonId: Int, orderNum: Int): Question?

    suspend fun getQuestionBylessonIdByOrderNumWithSignData(lessonId: Int, orderNum: Int): QuestionSignData?

    fun getQuestionsByLessonId(lessonId: Int): Flow<List<Question>>

    fun getQuestionsByQuestionId(questionId: Int): Flow<List<Question>>

    suspend fun getSignDataBySignId(signId: Int): signData?


}
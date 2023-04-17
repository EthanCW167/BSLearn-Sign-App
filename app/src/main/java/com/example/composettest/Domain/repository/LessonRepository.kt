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

    suspend fun getQuestionByIdWithSignData(questionId: Int): QuestionSignData?

    suspend fun getQuestionByIdByOrder(lessonId: Int, orderNum: Int): Question?

    suspend fun getQuestionBylessonIdByOrderNumWithSignData(lessonId: Int, orderNum: Int): QuestionSignData?

    //suspend fun collect(id: Int, qId: Int): Collect?
}
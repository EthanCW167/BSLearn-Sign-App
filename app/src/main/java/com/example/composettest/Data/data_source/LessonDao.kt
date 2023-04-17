package com.example.composettest.Data.data_source

import androidx.room.*
import com.example.composettest.Domain.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {

    @Query("SELECT * FROM lesson")
    fun getLessons(): Flow<List<Lesson>>

    @Query("SELECT * FROM lesson WHERE id = :id")
    suspend fun getLessonById(id: Int): Lesson?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: Lesson)

    @Delete
    suspend fun deleteLesson(lesson: Lesson)

    @Transaction
    @Query("SELECT * FROM lesson")
    fun getLessonsWithQuestions(): Flow<List<LessonQuestions>>

    @Transaction
    @Query("SELECT * FROM lesson WHERE id = :id")
    suspend fun getLessonByIdWithQuestions(id: Int): LessonQuestions?

    @Query("SELECT * FROM question WHERE questionId = :questionId")
    suspend fun getQuestionByIdWithSignData(questionId: Int): QuestionSignData?

    @Query("SELECT * FROM question WHERE lessonId = :lessonId AND orderNum = :orderNum")
    suspend fun getQuestionBylessonIdByOrderNumWithSignData(lessonId: Int, orderNum: Int): QuestionSignData?

    @Query("SELECT * FROM question WHERE lessonId = :lessonId AND orderNum = :orderNum")
    suspend fun getQuestionsByIdByOrder(lessonId: Int, orderNum: Int): Question?


}
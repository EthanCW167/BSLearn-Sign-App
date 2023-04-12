package com.example.composettest.Data.data_source

import androidx.room.*
import com.example.composettest.Domain.model.Lesson
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
}

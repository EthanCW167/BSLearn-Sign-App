package com.example.composettest.Data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composettest.Domain.model.Lesson


@Database(
    entities = [Lesson::class],
    version = 1
)
abstract class LessonDatabase: RoomDatabase() {

    abstract val lessonDao: LessonDao

    companion object{
        const val DATABASE_NAME = "lesson_database"
    }
}
package com.example.composettest.di

import android.app.Application
import androidx.room.Room
import com.example.composettest.Data.data_source.LessonDatabase
import com.example.composettest.Data.repository.LessonRepositoryImpl
import com.example.composettest.Domain.repository.LessonRepository
import com.example.composettest.Domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLessonDatabase(app: Application): LessonDatabase {
        return Room.databaseBuilder(
            app,
            LessonDatabase::class.java,
            LessonDatabase.DATABASE_NAME
        ).createFromAsset("database/LessonDatabase.db").build()
    }

    @Provides
    @Singleton
    fun provideLessonRepository(db: LessonDatabase): LessonRepository {
        return LessonRepositoryImpl(db.lessonDao)
    }

    @Provides
    @Singleton
    fun provideLessonUseCases(repository: LessonRepository): LessonUseCases {
        return LessonUseCases(
            getLessonsUseCase = GetLessonsUseCase(repository),
            deleteLessonUseCase = DeleteLessonUseCase(repository),
            getLessonUseCase = GetLessonUseCase(repository),
            getQuestionsByIdUseCase = GetQuestionsByIdUseCase(repository),
            getSignDataByIdUseCase = GetSignDataByIdUseCase(repository),
            getQuestionByQuestionIdUseCase = GetQuestionByQuestionIdUseCase(repository),
            getQuestionByIdByOrderUseCase = GetQuestionByIdByOrderUseCase(repository)
        )
    }
}
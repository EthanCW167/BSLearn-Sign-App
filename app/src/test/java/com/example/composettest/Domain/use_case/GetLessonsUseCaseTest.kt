package com.example.composettest.Domain.use_case

import com.example.composettest.Data.repository.LessonRepositoryImpl
import com.example.composettest.Domain.model.Lesson
import com.example.composettest.Domain.util.LessonOrder
import com.example.composettest.Domain.util.OrderType
import com.example.composettest.Repository.FakeLessonRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetLessonsUseCaseTest {

    private lateinit var getLessons: GetLessonsUseCase
    private lateinit var fakeRepository: FakeLessonRepository

    @Before
    fun setUp() {
        fakeRepository = FakeLessonRepository()
        getLessons = GetLessonsUseCase(fakeRepository)


        val lessonsToInsert = mutableListOf<Lesson>()
        (0..9).forEachIndexed { index, i ->
            lessonsToInsert.add(
                Lesson(
                    name = index.toString(),
                    lessonNum = i,
                    id = index,
                    questions = i,
                    signs = i,
                    isCompleted = 0,
                    previewFilePath = -1,
                    description = ""
                )
            )
        }
        lessonsToInsert.shuffle()
        runBlocking {
            lessonsToInsert.forEach { fakeRepository.insertLesson(it) }
        }
    }

    @Test
    fun order_lessons_by_lesson_number_ascending() = runBlocking {
        val lessons = getLessons(LessonOrder.LessonNum(OrderType.Ascending)).first()

        for (i in 0..lessons.size - 2) {
            assertThat(lessons[i].lessonNum).isLessThan(lessons[i + 1].lessonNum)
        }
    }

    @Test
    fun order_lessons_by_lesson_number_descending() = runBlocking {
        val lessons = getLessons(LessonOrder.LessonNum(OrderType.Descending)).first()

        for (i in 0..lessons.size - 2) {
            assertThat(lessons[i].lessonNum).isGreaterThan(lessons[i + 1].lessonNum)
        }
    }
}
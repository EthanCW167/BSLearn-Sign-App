package com.example.composettest.LessonMaker

sealed class EditLessonEvent{
    data class EnteredTitle(val value: String): EditLessonEvent()
    data class SaveLesson(val lessonId: String): EditLessonEvent()
}

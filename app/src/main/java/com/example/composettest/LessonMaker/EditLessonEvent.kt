package com.example.composettest.LessonMaker

import com.example.composettest.Domain.model.FSignData

sealed class EditLessonEvent{
    data class EnteredTitle(val value: String): EditLessonEvent()
    data class SaveLesson(val lessonId: String): EditLessonEvent()
    data class SetQuestionType(val questionType: String): EditLessonEvent()
    data class SetSignData(val signName: String): EditLessonEvent()
    data class SaveQuestion(val lessonId: String, val questionIndex: Int): EditLessonEvent()
}

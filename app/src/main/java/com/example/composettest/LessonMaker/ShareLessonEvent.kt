package com.example.composettest.LessonMaker

sealed class ShareLessonEvent{
    data class AddUser(val userId: String): ShareLessonEvent()
}

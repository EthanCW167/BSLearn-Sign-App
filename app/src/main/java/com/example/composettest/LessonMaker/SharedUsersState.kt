package com.example.composettest.LessonMaker

import com.example.composettest.Domain.model.User

data class SharedUsersState(
    val Users: List<User> = emptyList()
)

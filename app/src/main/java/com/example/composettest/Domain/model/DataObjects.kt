package com.example.composettest.Domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Person(
    val firstName: String,
    val lastName: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$firstName$lastName",
            "$firstName $lastName",
            "${firstName.first()} ${lastName.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

data class signData(
    val sign: String,
    val filePath: Int,
    val previewFilePath: Int
)
@Entity
data class Lesson(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val lessonNum: Int,
    val signs: Int,
    val questions: Int,
    val listOfQuestions: MutableList<LessonData> = mutableListOf(),
    val isCompleted: Boolean
)


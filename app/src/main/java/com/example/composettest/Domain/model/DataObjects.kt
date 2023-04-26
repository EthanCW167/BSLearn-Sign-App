package com.example.composettest.Domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

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

@Entity(tableName = "lesson")
data class Lesson(
    @PrimaryKey val id: Int?,
    val name: String,
    val lessonNum: Int,
    val signs: Int,
    val questions: Int,
    val isCompleted: Int,
    val previewFilePath: Int,
    val description: String
)

@Entity(tableName = "question")
data class Question(
    @PrimaryKey val questionId: Int,
    val orderNum: Int,
    val lessonId: Int?,
    val questionType: String,
    val signId: Int,
    val isCorrect: Int
)

@Entity(tableName = "signData")
data class signData(
    @PrimaryKey val signId: Int,
    val sign: String,
    val filePath: Int,
    val previewFilePath: Int
)

data class LessonQuestions(
    @Embedded val lesson: Lesson,
    @Relation(
        parentColumn = "id",
        entityColumn = "lessonId"
    )
    val lessonQuestions : MutableList<Question>

)

data class QuestionSignData(
    @Embedded val questionSignData: Question,
    @Relation(
        parentColumn = "signId",
        entityColumn = "signId"
    )
    val signData: signData
)


data class FLesson(
    val author: Int = 0,
    val name: String = "",
    val lessonNum: Int = 0,
    val signs: Int = 0,
    val questions: Int = 0,
    val questionsList: List<FQuestion> = emptyList(),
    val previewFilePath: Int = 0,
    val description: String = ""
)

data class FQuestion(
    val questionId: Int = 0,
    val questionType: String = "",
    val signData: FSignData = FSignData(0,"", 0 , 0),
    val isCorrect: Int = 0
)


data class FSignData(
    val signId: Int = 0,
    val sign: String = "",
    val filePath: Int = 0,
    val previewFilePath: Int = 0
)
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
    val author: String = "-1",
    var name: String = "Lesson Name",
    val lessonNum: Int = 1,
    val signs: Int = 1,
    val questions: Int = 0,
    var questionsList: List<FQuestion> = listOf(FQuestion()),
    val previewFilePath: Int = 0,
    val description: String = "",
    var sharedUserList: List<String> = emptyList()
)

data class FQuestion(
    val questionId: Int = 0,
    var questionType: String = "sign",
    var signData: FSignData = FSignData(),
    val isCorrect: Int = 0
)


data class FSignData(
    val signId: Int = 1,
    val sign: String = "Hello",
    val filePath: Int = 2131623936,
    val previewFilePath: Int = 2131165305
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$sign",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

data class User(
    val name: String = "Name",
    val profilePicturePath: Int = -1,
    val sharedLesson: MutableList<String> = mutableListOf()
)
